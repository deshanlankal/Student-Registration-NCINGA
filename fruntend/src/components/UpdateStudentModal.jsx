import React, { useEffect, useState } from 'react';
import './UpdateStudentForm.css';
import {toast} from 'react-toastify';

const UpdateStudentModal = ({ student, onClose, onUpdate }) => {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    phoneNumber: '',
    address: '',
    dob: '',
    course: '',
    gender: '',
    imageFile: null,
    imagePreview: null,
  });

  useEffect(() => {
    if (student) {
      setFormData({
        firstName: student.firstName || '',
        lastName: student.lastName || '',
        email: student.email || '',
        phoneNumber: student.phoneNumber || '',
        address: student.address || '',
        dob: student.dob || '',
        course: student.course || '',
        gender: student.gender || '',
        imageFile: null,
        imagePreview: student.imageData
          ? `data:${student.imageType};base64,${student.imageData}`
          : null,
      });
    }
  }, [student]);

  const handleChange = (e) => {
    const { name, value } = e.target;
  
    let newValue = value;
  
    if (name === 'firstName' || name === 'lastName') {
      // Allow only letters and spaces
      newValue = value.replace(/[^a-zA-Z\s]/g, '');
    }
  
    if (name === 'phoneNumber') {
      // Allow only digits
      newValue = value.replace(/[^0-9]/g, '');
    }
  
    setFormData((prev) => ({
      ...prev,
      [name]: newValue,
    }));
  };
  

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      setFormData((prev) => ({
        ...prev,
        imageFile: file,
        imagePreview: URL.createObjectURL(file),
      }));
    }
  };

  const handleImageClick = () => {
    document.getElementById('imageInput').click();
  };

  const [isUpdating, setIsUpdating] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsUpdating(true);

    // Email validation
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(formData.email)) {
      alert('Please enter a valid email address.');
      return;
    }

    const studentData = {
      firstName: formData.firstName,
      lastName: formData.lastName,
      email: formData.email,
      phoneNumber: formData.phoneNumber,
      address: formData.address,
      dob: formData.dob,
      course: formData.course,
      gender: formData.gender,
    };

    const data = new FormData();
    data.append('student', JSON.stringify(studentData));
    if (formData.imageFile) {
      data.append('imageFile', formData.imageFile);
    }

    try {
      const response = await fetch(`http://localhost:8080/api/students/student/${student.id}`, {
        method: 'PUT',
        body: data,
      });

      if (response.ok) {
        const updatedStudent = await response.json();
        onUpdate(updatedStudent);
        toast.success('Update suuccessful!');
      } else {
        toast.error('Update failed');
      }
    } catch (err) {
      console.error('Update error:', err);
      toast.error('Update error:', err);
    }finally {
      setIsUpdating(false);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal">
        <h2>Update Student</h2>

        <div className="image-center">
          <input
            type="file"
            id="imageInput"
            accept="image/*"
            onChange={handleImageChange}
            style={{ display: 'none' }}
          />
          <img
            src={formData.imagePreview || '/placeholder.jpg'}
            alt="Preview"
            className="image-preview clickable"
            onClick={handleImageClick}
          />
        </div>

        <form onSubmit={handleSubmit} className="modal-form">
          <div className="form-grid">
            <input
              type="text"
              name="firstName"
              value={formData.firstName}
              onChange={handleChange}
              placeholder="First Name"
              required
            />
            <input
              type="text"
              name="lastName"
              value={formData.lastName}
              onChange={handleChange}
              placeholder="Last Name"
              required
            />
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              placeholder="Email"
              required
            />
            <input
              type="text"
              name="phoneNumber"
              value={formData.phoneNumber}
              onChange={handleChange}
              placeholder="Phone Number"
              required
            />
            <input
              type="text"
              name="address"
              value={formData.address}
              onChange={handleChange}
              placeholder="Address"
              required
            />
            <input
              type="date"
              name="dob"
              value={formData.dob}
              onChange={handleChange}
              placeholder="Date of Birth"
              required
            />

            <select
              name="course"
              value={formData.course}
              onChange={handleChange}
              required
            >
              <option value="">Select Course</option>
              <option value="Computer Science">Computer Science</option>
              <option value="Mechanical Engineering">Mechanical Engineering</option>
              <option value="Business Administration">Business Administration</option>
              <option value="Psychology">Psychology</option>
              <option value="Design">Design</option>
            </select>

            <select
              name="gender"
              value={formData.gender}
              onChange={handleChange}
              required
            >
              <option value="">Select Gender</option>
              <option value="Male">Male</option>
              <option value="Female">Female</option>
              <option value="Non-binary">Non-binary</option>
              <option value="Transgender">Transgender</option>
              <option value="Genderqueer">Genderqueer</option>
              <option value="Agender">Agender</option>
              <option value="Bigender">Bigender</option>
              <option value="Genderfluid">Genderfluid</option>
              <option value="Two-Spirit">Two-Spirit</option>
              <option value="Prefer not to say">Prefer not to say</option>
            </select>
          </div>

          <div className="modal-actions">
            {/* <button type="submit">Update</button> */}
            <button type="submit" disabled={isUpdating}>
              {isUpdating ? 'Updating...' : 'Update'}
            </button>

            <button type="button" onClick={onClose} className="close-btn">Cancel</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default UpdateStudentModal;
