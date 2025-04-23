import React, { useEffect, useState, useCallback } from 'react';
import { useLocation } from 'react-router-dom';
import StudentCard from '../components/StudentCard';
import { getAllStudents, searchStudents } from '../api';
import UpdateStudentForm from '../components/UpdateStudentModal';
import { toast } from 'react-toastify';
import './Home.css'; 

const Home = () => {
  const [students, setStudents] = useState([]);
  const [selectedStudent] = useState(null);
  const [showUpdateForm, setShowUpdateForm] = useState(false);
  const [page, setPage] = useState(1);
  const [hasMore, setHasMore] = useState(true);
  const location = useLocation();

  const query = new URLSearchParams(location.search).get('search');

  // Fetch students based on query and pagination
  const fetchStudents = useCallback(async () => {
    try {
      const res = query
        ? await searchStudents(query, page, 9)
        : await getAllStudents(page, 9);

      if (res.data.length < 9) setHasMore(false);
      else setHasMore(true);

      if (page === 1) {
        setStudents(res.data);
      } else {
        setStudents((prev) => [...prev, ...res.data]);
      }
    } catch (err) {
      console.error('Failed to fetch students', err);
      toast.error('Failed to fetch students');
    }
  }, [page, query]);

  // Reset page to 1 when query changes
  useEffect(() => {
    setPage(1);
  }, [query]);

  // Fetch students on page or query change
  useEffect(() => {
    fetchStudents();
  }, [fetchStudents]);

  const handleUpdate = (updatedStudent) => {
    setStudents((prevStudents) =>
      prevStudents.map((student) =>
        student.id === updatedStudent.id ? updatedStudent : student
      )
    );
    setShowUpdateForm(false);
  };

  const handleDelete = (id) => {
    setStudents((prevStudents) => prevStudents.filter((student) => student.id !== id));
    toast.success('Student deleted successfully!');
  };

  return (
    <div className="home-container">
      {/* Update Modal */}
      {showUpdateForm && selectedStudent && (
        <UpdateStudentForm
          student={selectedStudent}
          onUpdate={handleUpdate}
          onClose={() => setShowUpdateForm(false)}
        />
      )}

      {/* Student Cards Grid */}
      <div className="card-grid">
        {students.map((student) => (
          <StudentCard
            key={student.id}
            student={student}
            onDelete={handleDelete}
            onUpdate={handleUpdate}
          />
        ))}
      </div>

      {/* Pagination */}
      {hasMore && (
        <div className="pagination-container">
          <button className="next-btn" onClick={() => setPage((prev) => prev + 1)}>
            Load More
          </button>
        </div>
      )}
    </div>
  );
};

export default Home;
