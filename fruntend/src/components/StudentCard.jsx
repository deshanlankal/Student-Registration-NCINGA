import React, { useState } from 'react';
import ReactCardFlip from 'react-card-flip';
import { toast } from 'react-toastify';
import UpdateStudentForm from './UpdateStudentModal';
import './StudentCard.css';  


const StudentCard = ({ student, onDelete, onUpdate }) => {
  const [isFlipped, setIsFlipped] = useState(false);
  const [showUpdate, setShowUpdate] = useState(false);

  const handleMouseEnter = () => setIsFlipped(true);
  const handleMouseLeave = () => setIsFlipped(false);

  const handleDelete = async () => {
    if (window.confirm(`Are you sure you want to delete ${student.firstName}?`)) {
      try {
        const response = await fetch(`http://localhost:8080/api/students/${student.id}`, {
          method: 'DELETE',
        });

        if (response.ok) {
          toast.success('Student deleted successfully!');
          if (onDelete) onDelete(student.id);
        } else {
          toast.error('Failed to delete student');
        }
      } catch (err) {
        console.error(err);
        toast.error('Error deleting student');
      }
    }
  };

  const handleUpdateClick = () => setShowUpdate(true);
  const handleUpdateClose = () => setShowUpdate(false);

  const handleUpdateSubmit = (updatedStudent) => {
    if (onUpdate) onUpdate(updatedStudent);
    setShowUpdate(false);
  };

  return (
    <>
      <div className="card-container">
        <ReactCardFlip isFlipped={isFlipped} flipDirection="horizontal">
          {/* Front */}
          <div
            className="card"
            onMouseEnter={handleMouseEnter}
            onMouseLeave={handleMouseLeave}
          >
            <img
              src={
                student.imageData
                  ? `data:${student.imageType};base64,${student.imageData}`
                  : 'https://via.placeholder.com/150'
              }
              alt="Profile"
              className="card-img"
            />
            <h3>{student.firstName} {student.lastName}</h3>
            <p>{student.email}</p>

          </div>

          {/* Back */}
          <div
            className="card"
            onMouseEnter={handleMouseEnter}
            onMouseLeave={handleMouseLeave}
          >
            <div className="card-info">
              <p><strong>Email:</strong> {student.email}</p>
              <p><strong>Course:</strong> {student.course}</p>
              <p><strong>Phone:</strong> {student.phoneNumber}</p>
              <p><strong>Address:</strong> {student.address}</p>
              <p><strong>DOB:</strong> {student.dob}</p>
              <p><strong>Gender:</strong> {student.gender}</p>
              <button className="update-btn" onClick={handleUpdateClick}>Update</button>
              <button className="delete-btn" onClick={handleDelete}>Delete</button>
            </div>
          </div>
        </ReactCardFlip>
      </div>

      {/* Render update form conditionally */}
      {showUpdate && (
        <UpdateStudentForm
          student={student}
          onClose={handleUpdateClose}
          onUpdate={handleUpdateSubmit}
        />
      )}
    </>
  );
};

export default StudentCard;
