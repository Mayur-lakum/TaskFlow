import React, { useState, useEffect } from 'react';
import { getAllProjects, createProject, updateProject, deleteProject } from '../services/projectService';
import { getAllSkills } from '../services/skillService';
import './Projects.css';

const Projects = () => {
  const [projects, setProjects] = useState([]);
  const [skills, setSkills] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [showForm, setShowForm] = useState(false);
  const [editingProject, setEditingProject] = useState(null);
  const [selectedSkills, setSelectedSkills] = useState([]);
  const [formData, setFormData] = useState({
    projectCode: '',
    projectName: '',
    description: '',
    clientName: '',
    startDate: '',
    endDate: '',
    requiredExperience: 0,
    status: 'PLANNING'
  });

  useEffect(() => {
    fetchProjects();
    fetchSkills();
  }, []);

  const fetchProjects = async () => {
    setLoading(true);
    setError('');
    try {
      const projects = await getAllProjects();
      setProjects(projects);
    } catch (error) {
      console.error('Error fetching projects:', error);
      setError(error.response?.data?.message || 'Failed to fetch projects');
    } finally {
      setLoading(false);
    }
  };

  const fetchSkills = async () => {
    try {
      const skillsData = await getAllSkills();
      setSkills(skillsData);
    } catch (error) {
      console.error('Error fetching skills:', error);
    }
  };

  const handleShowForm = (project = null) => {
    if (project) {
      setEditingProject(project);
      setFormData({
        projectCode: project.projectCode,
        projectName: project.projectName,
        description: project.description || '',
        clientName: project.clientName,
        startDate: project.startDate || '',
        endDate: project.endDate || '',
        requiredExperience: project.requiredExperience,
        status: project.status || 'PLANNING'
      });
      // Pre-select skills from project
      if (project.requiredSkills) {
        setSelectedSkills(project.requiredSkills.map(skill => skill.id));
      } else {
        setSelectedSkills([]);
      }
    } else {
      setEditingProject(null);
      setFormData({
        projectCode: '',
        projectName: '',
        description: '',
        clientName: '',
        startDate: '',
        endDate: '',
        requiredExperience: 0,
        status: 'PLANNING'
      });
      setSelectedSkills([]);
    }
    setShowForm(true);
  };

  const handleCloseForm = () => {
    setShowForm(false);
    setEditingProject(null);
    setSelectedSkills([]);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSkillToggle = (skillId) => {
    setSelectedSkills(prev => {
      if (prev.includes(skillId)) {
        return prev.filter(id => id !== skillId);
      } else {
        return [...prev, skillId];
      }
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    // Validate all required fields per backend DTO
    if (!formData.projectCode || formData.projectCode.trim() === "") {
      alert('Project Code is required');
      return;
    }
    if (!formData.projectName || formData.projectName.trim() === "") {
      alert('Project Name is required');
      return;
    }
    if (!formData.clientName || formData.clientName.trim() === "") {
      alert('Client Name is required');
      return;
    }
    if (!formData.startDate) {
      alert('Start Date is required');
      return;
    }
    
    // Ensure requiredExperience is a valid integer >= 0
    const exp = parseInt(formData.requiredExperience);
    if (isNaN(exp) || exp < 0) {
      alert('Required Experience must be a number >= 0');
      return;
    }
    
    // Build data matching backend ProjectRequestDTO exactly
    const submitData = {
      projectCode: formData.projectCode.trim(),
      projectName: formData.projectName.trim(),
      description: formData.description ? formData.description.trim() : "",
      clientName: formData.clientName.trim(),
      startDate: formData.startDate,
      endDate: formData.endDate || null,
      requiredExperience: exp,
      status: formData.status || "PLANNING"
    };
    
    // Add skillIds if skills are selected
    if (selectedSkills.length > 0) {
      submitData.skillIds = selectedSkills;
    }
    
    try {
      if (editingProject) {
        await updateProject(editingProject.id, submitData);
        alert('Project updated successfully!');
      } else {
        await createProject(submitData);
        alert('Project created successfully!');
      }
      handleCloseForm();
      fetchProjects();
    } catch (error) {
      console.error('Error:', error);
      alert(error.response?.data?.message || 'Failed to save project');
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this project?')) {
      try {
        await deleteProject(id);
        alert('Project deleted successfully!');
        fetchProjects();
      } catch (error) {
        console.error('Error deleting project:', error);
        alert(error.response?.data?.message || 'Failed to delete project');
      }
    }
  };

  return (
    <div className="projects-page">
      <div className="page-header">
        <h2>Projects</h2>
        <button className="btn btn-primary" onClick={() => handleShowForm(null)}>
          Add Project
        </button>
      </div>

      {error && (
        <div className="alert alert-danger" style={{ marginBottom: '20px' }}>{error}</div>
      )}

      {loading ? (
        <div className="text-center">Loading...</div>
      ) : (
        <div className="table-responsive">
          <table className="table">
            <thead>
              <tr>
                <th>Project Code</th>
                <th>Project Name</th>
                <th>Client</th>
                <th>Required Experience</th>
                <th>Required Skills</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {projects.map(project => (
                <tr key={project.id}>
                  <td>{project.projectCode}</td>
                  <td>{project.projectName}</td>
                  <td>{project.clientName}</td>
                  <td>{project.requiredExperience} years</td>
                  <td>
                    {project.requiredSkills && project.requiredSkills.length > 0 ? (
                      <div>
                        {project.requiredSkills.slice(0, 2).map(skill => (
                          <span key={skill.id} className="badge badge-success" style={{ marginRight: '3px', fontSize: '11px' }}>
                            {skill.skillName}
                          </span>
                        ))}
                        {project.requiredSkills.length > 2 && (
                          <span className="badge badge-secondary" style={{ fontSize: '11px' }}>
                            +{project.requiredSkills.length - 2}
                          </span>
                        )}
                      </div>
                    ) : (
                      <span className="badge badge-secondary" style={{ fontSize: '11px' }}>No skills</span>
                    )}
                  </td>
                  <td>
                    <span className={`badge ${project.status === 'IN_PROGRESS' ? 'badge-success' : 'badge-secondary'}`}>
                      {project.status.replace('_', ' ')}
                    </span>
                  </td>
                  <td>
                    <button 
                      className="btn btn-sm btn-info"
                      onClick={() => handleShowForm(project)}
                      style={{ marginRight: '5px' }}
                    >
                      Edit
                    </button>
                    <button 
                      className="btn btn-sm btn-danger"
                      onClick={() => handleDelete(project.id)}
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {showForm && (
        <div style={{
          backgroundColor: 'white',
          padding: '20px',
          borderRadius: '10px',
          boxShadow: '0 4px 20px rgba(0,0,0,0.1)',
          margin: '20px 0',
          border: '2px solid #3498db'
        }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '15px' }}>
            <h3>{editingProject ? 'Edit Project' : 'Add Project'}</h3>
            <button onClick={handleCloseForm} style={{ background: '#dc3545', color: 'white', border: 'none', padding: '5px 10px', borderRadius: '4px', cursor: 'pointer' }}>Close</button>
          </div>
          <form onSubmit={handleSubmit}>
            <div style={{ marginBottom: '10px' }}>
              <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>Project Code *</label>
              <input
                type="text"
                name="projectCode"
                value={formData.projectCode}
                onChange={handleChange}
                style={{ width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px' }}
                required
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>Project Name *</label>
              <input
                type="text"
                name="projectName"
                value={formData.projectName}
                onChange={handleChange}
                style={{ width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px' }}
                required
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>Description</label>
              <textarea
                name="description"
                value={formData.description}
                onChange={handleChange}
                style={{ width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px' }}
                rows="3"
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>Client Name *</label>
              <input
                type="text"
                name="clientName"
                value={formData.clientName}
                onChange={handleChange}
                style={{ width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px' }}
                required
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>Start Date *</label>
              <input
                type="date"
                name="startDate"
                value={formData.startDate}
                onChange={handleChange}
                style={{ width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px' }}
                required
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>End Date</label>
              <input
                type="date"
                name="endDate"
                value={formData.endDate}
                onChange={handleChange}
                style={{ width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px' }}
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>Required Experience (years) *</label>
              <input
                type="number"
                name="requiredExperience"
                value={formData.requiredExperience}
                onChange={handleChange}
                style={{ width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px' }}
                required
                min="0"
                step="1"
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>Status *</label>
              <select
                name="status"
                value={formData.status}
                onChange={handleChange}
                style={{ width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px' }}
                required
              >
                <option value="PLANNING">Planning</option>
                <option value="IN_PROGRESS">In Progress</option>
                <option value="ON_HOLD">On Hold</option>
                <option value="COMPLETED">Completed</option>
                <option value="CANCELLED">Cancelled</option>
              </select>
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>Required Skills *</label>
              <div style={{ 
                border: '1px solid #ccc', 
                borderRadius: '4px', 
                padding: '10px', 
                maxHeight: '150px', 
                overflowY: 'auto',
                backgroundColor: '#f9f9f9'
              }}>
                {skills.length === 0 ? (
                  <div style={{ color: '#666', fontStyle: 'italic' }}>Loading skills...</div>
                ) : (
                  skills.map(skill => (
                    <div key={skill.id} style={{ marginBottom: '5px' }}>
                      <label style={{ 
                        display: 'flex', 
                        alignItems: 'center', 
                        cursor: 'pointer',
                        fontSize: '14px'
                      }}>
                        <input
                          type="checkbox"
                          checked={selectedSkills.includes(skill.id)}
                          onChange={() => handleSkillToggle(skill.id)}
                          style={{ marginRight: '8px' }}
                        />
                        {skill.skillName}
                      </label>
                    </div>
                  ))
                )}
              </div>
              <div style={{ fontSize: '12px', color: '#666', marginTop: '5px' }}>
                {selectedSkills.length} skill(s) selected
              </div>
            </div>
            <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '10px', marginTop: '15px' }}>
              <button type="button" onClick={handleCloseForm} style={{ padding: '8px 16px', borderRadius: '4px', background: '#6c757d', color: 'white', border: 'none', cursor: 'pointer' }}>
                Cancel
              </button>
              <button type="submit" style={{ padding: '8px 16px', borderRadius: '4px', background: '#3498db', color: 'white', border: 'none', cursor: 'pointer' }}>
                {editingProject ? 'Update' : 'Create'}
              </button>
            </div>
          </form>
        </div>
      )}
    </div>
  );
};

export default Projects;