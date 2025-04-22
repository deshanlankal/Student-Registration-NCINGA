import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Search } from 'lucide-react';
import './Header.css';

const Header = () => {
  const [search, setSearch] = useState('');
  const navigate = useNavigate();

  const handleSearch = (e) => {
    if (e.key === 'Enter') {
      navigate(`/?search=${search}`);
    }
  };

  const handleAddStudent = () => {
    navigate('/add');
  };


  return (
    <header className="header">
       <Search className="search-icon" />
      <input
        type="text"
        placeholder="Search..."
        value={search}
        onChange={(e) => setSearch(e.target.value)}
        onKeyDown={handleSearch}
        className="search-input"
      />
      <button onClick={handleAddStudent} className="add-btn">
        Add Student
      </button>
    </header>
  );
};

export default Header;
