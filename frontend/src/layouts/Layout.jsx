import React from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { logout } from '../services/authService';
import './Layout.css';

const Layout = ({ children }) => {
  const location = useLocation();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  const isActive = (path) => location.pathname === path;

  return (
    <div className="layout-container">
      <div className="sidebar">
        <div className="sidebar-header">
          <h3>TaskFlow AI</h3>
        </div>
        <nav className="sidebar-nav">
          <Link 
            to="/dashboard" 
            className={`nav-link ${isActive('/dashboard') ? 'active' : ''}`}
          >
            Dashboard
          </Link>
          <Link 
            to="/employees" 
            className={`nav-link ${isActive('/employees') ? 'active' : ''}`}
          >
            Employees
          </Link>
          <Link 
            to="/projects" 
            className={`nav-link ${isActive('/projects') ? 'active' : ''}`}
          >
            Projects
          </Link>
          <Link 
            to="/skills" 
            className={`nav-link ${isActive('/skills') ? 'active' : ''}`}
          >
            Skills
          </Link>
          <Link 
            to="/recommendations" 
            className={`nav-link ${isActive('/recommendations') ? 'active' : ''}`}
          >
            Recommendations
          </Link>
        </nav>
        <div className="sidebar-footer">
          <button onClick={handleLogout} className="btn-logout">
            Logout
          </button>
        </div>
      </div>
      
      <div className="main-content">
        <header className="navbar">
          <div className="navbar-brand">
            <h4>TaskFlow AI</h4>
          </div>
          <div className="navbar-user">
            <span className="user-role">Admin</span>
          </div>
        </header>
        <main className="content">
          {children}
        </main>
      </div>
    </div>
  );
};

export default Layout;