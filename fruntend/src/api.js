import axios from 'axios';

const API_BASE = 'http://localhost:8080/api/students'; // adjust if needed

// Get students with pagination
export const getAllStudents = (page = 1, limit = 9) =>
  axios.get(`${API_BASE}?page=${page}&limit=${limit}`);

// Search students with pagination
// make a anotrher api for search students
export const searchStudents = (text, page = 0, size = 9) =>
  axios.get(`${API_BASE}/search/${text}?page=${page}&size=${size}`);

// export const searchStudents = (text, page = 1, limit = 9) =>
//   axios.get(`${API_BASE}/search/${text}?page=${page}&limit=${limit}`);

export const addStudent = (data) =>
  axios.post(`${API_BASE}`, data, {
    headers: { 'Content-Type': 'multipart/form-data' },
  });

export const deleteStudent = (id) => axios.delete(`${API_BASE}/${id}`);

export const updateStudent = (id, data) =>
  axios.put(`${API_BASE}/${id}`, data, {
    headers: { 'Content-Type': 'multipart/form-data' },
  });
