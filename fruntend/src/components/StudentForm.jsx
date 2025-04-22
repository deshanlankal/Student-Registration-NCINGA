import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './AddStudentForm.css';

const AddStudentForm = () => {
  const navigate = useNavigate();
  const [isSubmitting, setIsSubmitting] = useState(false);

  const [student, setStudent] = useState({
    firstName: '',
    lastName: '',
    email: '',
    course: '',
    phoneNumber: '',
    address: '',
    dob: '',
    gender: '',
  });

  const [imageFile, setImageFile] = useState(null);
  const [imagePreview, setImagePreview] = useState(null);

  const handleChange = (e) => {
    const { name, value } = e.target;

    if ((name === 'firstName' || name === 'lastName') && /\d/.test(value)) return;
    if (name === 'phoneNumber' && /\D/.test(value)) return;

    setStudent({ ...student, [name]: value });
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    setImageFile(file);
    if (file) {
      setImagePreview(URL.createObjectURL(file));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsSubmitting(true);

    const formData = new FormData();
    formData.append('student', JSON.stringify(student));
    if (imageFile) formData.append('imageFile', imageFile);

    try {
      await axios.post('http://localhost:8080/api/students/student', formData, {
        headers: { 'Content-Type': 'multipart/form-data' },
      });
      toast.success('Student added successfully!');
      navigate('/');
    } catch (error) {
      console.error('Error:', error.response);
      toast.error(`Something went wrong!\n${error.response?.data || error.message}`);
    }finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="student-form-wrapper">
      <h2 className="form-title">Add New Student</h2>
      <form onSubmit={handleSubmit} className="student-form">
        <div className="form-grid">
          {[
            { name: 'firstName', type: 'text', placeholder: 'First Name' },
            { name: 'lastName', type: 'text', placeholder: 'Last Name' },
            { name: 'email', type: 'email', placeholder: 'Email' },
            { name: 'phoneNumber', type: 'text', placeholder: 'Phone Number' },
            { name: 'address', type: 'text', placeholder: 'Address' },
            { name: 'dob', type: 'date', placeholder: 'Date of Birth' },
          ].map(({ name, type, placeholder }) => (
            <div className="form-group" key={name}>
              <input
                type={type}
                name={name}
                value={student[name]}
                onChange={handleChange}
                onBlur={
                  name === 'email'
                    ? (e) => {
                        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                        if (!emailRegex.test(e.target.value)) toast.error('Invalid email format');
                      }
                    : undefined
                }
                required
              />
              <label className={student[name] ? 'filled' : ''}>{placeholder}</label>
            </div>
          ))}

          <div className="form-group">
            <select name="course" value={student.course} onChange={handleChange} required>
              <option value="">Select Course</option>
              <option>Computer Science</option>
              <option>Mechanical Engineering</option>
              <option>Business Administration</option>
              <option>Psychology</option>
              <option>Design</option>
            </select>
            <label className="filled">Course</label>
          </div>

          <div className="form-group">
            <select name="gender" value={student.gender} onChange={handleChange} required>
              <option value="">Select Gender</option>
              <option>Male</option>
              <option>Female</option>
              <option>Non-binary</option>
              <option>Transgender</option>
              <option>Genderqueer</option>
              <option>Agender</option>
              <option>Bigender</option>
              <option>Genderfluid</option>
              <option>Two-Spirit</option>
              <option>Prefer not to say</option>
            </select>
            <label className="filled">Gender</label>
          </div>
        </div>

        <div className="image-upload">
          <label>Profile Image</label>
          <input type="file" accept="image/*" onChange={handleImageChange} />
          {imagePreview && <img src={imagePreview} alt="Preview" className="image-preview" />}
        </div>

        {/* <button type="submit" className="submit-btn">Done</button> */}
        <button type="submit" className="submit-btn" disabled={isSubmitting}>
          {isSubmitting ? 'Submitting...' : 'Done'}
        </button>

      </form>
    </div>
  );
};

export default AddStudentForm;
