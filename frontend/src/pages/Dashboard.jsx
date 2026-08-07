import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { getAllEmployees } from '../services/employeeService';
import { getAllProjects } from '../services/projectService';
import { getAllSkills } from '../services/skillService';
import './Dashboard.css';

const Dashboard = () => {
  const [stats, setStats] = useState({
    totalEmployees: 0,
    totalProjects: 0,
    totalSkills: 0,
    availableEmployees: 0
  });
  const [recentEmployees, setRecentEmployees] = useState([]);
  const [recentProjects, setRecentProjects] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    fetchDashboardData();
  }, []);

  const fetchDashboardData = async () => {
    setError('');
    try {
      const [employees, projects, skills] = await Promise.all([
        getAllEmployees(),
        getAllProjects(),
        getAllSkills()
      ]);

      setStats({
        totalEmployees: employees.length,
        totalProjects: projects.length,
        totalSkills: skills.length,
        availableEmployees: employees.filter(emp => emp.availability).length
      });

      setRecentEmployees(employees.slice(-5).reverse());
      setRecentProjects(projects.slice(-5).reverse());
    } catch (error) {
      console.error('Error fetching dashboard data:', error);
      setError(error.response?.data?.message || 'Failed to fetch dashboard data');
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="text-center mt-5">Loading...</div>;
  }

  return (
    <div className="dashboard">
      <h2 className="dashboard-title">Dashboard</h2>
      
      {error && (
        <div className="alert alert-danger" style={{ marginBottom: '20px' }}>{error}</div>
      )}
      
      <div className="stats-grid">
        <div className="stat-card">
          <div className="stat-icon employees-icon">
            👥
          </div>
          <div className="stat-content">
            <h3>{stats.totalEmployees}</h3>
            <p>Total Employees</p>
          </div>
        </div>

        <div className="stat-card">
          <div className="stat-icon projects-icon">
            💼
          </div>
          <div className="stat-content">
            <h3>{stats.totalProjects}</h3>
            <p>Total Projects</p>
          </div>
        </div>

        <div className="stat-card">
          <div className="stat-icon skills-icon">
            🛠️
          </div>
          <div className="stat-content">
            <h3>{stats.totalSkills}</h3>
            <p>Total Skills</p>
          </div>
        </div>

        <div className="stat-card">
          <div className="stat-icon available-icon">
            ✅
          </div>
          <div className="stat-content">
            <h3>{stats.availableEmployees}</h3>
            <p>Available Employees</p>
          </div>
        </div>
      </div>

      <div className="dashboard-actions">
        <button 
          className="btn btn-primary btn-recommend"
          onClick={() => navigate('/recommendations')}
        >
          Quick Recommendation
        </button>
      </div>

      <div className="dashboard-sections">
        <div className="section">
          <h3>Recent Employees</h3>
          <div className="table-responsive">
            <table className="table">
              <thead>
                <tr>
                  <th>Code</th>
                  <th>Name</th>
                  <th>Department</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                {recentEmployees.map(employee => (
                  <tr key={employee.id}>
                    <td>{employee.employeeCode || 'N/A'}</td>
                    <td>{`${employee.firstName} ${employee.lastName}`}</td>
                    <td>{employee.department}</td>
                    <td>
                      <span className={`badge ${employee.availability ? 'badge-success' : 'badge-warning'}`}>
                        {employee.availability ? 'Available' : 'Busy'}
                      </span>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>

        <div className="section">
          <h3>Recent Projects</h3>
          <div className="table-responsive">
            <table className="table">
              <thead>
                <tr>
                  <th>Code</th>
                  <th>Name</th>
                  <th>Client</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                {recentProjects.map(project => (
                  <tr key={project.id}>
                    <td>{project.projectCode}</td>
                    <td>{project.projectName}</td>
                    <td>{project.clientName}</td>
                    <td>
                      <span className="badge badge-info">{project.status.replace('_', ' ')}</span>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;