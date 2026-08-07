import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './pages/Login';
import Layout from './layouts/Layout';
import Dashboard from './pages/Dashboard';
import Employees from './pages/Employees';
import Projects from './pages/Projects';
import Skills from './pages/Skills';
import Recommendations from './pages/Recommendations';
import { isAuthenticated } from './utils/auth';
import './App.css';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        
        <Route path="/dashboard" element={
          isAuthenticated() ? <Layout><Dashboard /></Layout> : <Navigate to="/login" replace />
        } />
        <Route path="/employees" element={
          isAuthenticated() ? <Layout><Employees /></Layout> : <Navigate to="/login" replace />
        } />
        <Route path="/projects" element={
          isAuthenticated() ? <Layout><Projects /></Layout> : <Navigate to="/login" replace />
        } />
        <Route path="/skills" element={
          isAuthenticated() ? <Layout><Skills /></Layout> : <Navigate to="/login" replace />
        } />
        <Route path="/recommendations" element={
          isAuthenticated() ? <Layout><Recommendations /></Layout> : <Navigate to="/login" replace />
        } />
        
        <Route path="/" element={<Navigate to="/dashboard" replace />} />
        <Route path="*" element={<Navigate to="/login" replace />} />
      </Routes>
    </Router>
  );
}

export default App;