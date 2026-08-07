import React, { useState, useEffect } from 'react';
import { getAllSkills, createSkill, updateSkill, deleteSkill, assignSkillToEmployee, removeSkillFromEmployee, getEmployeeSkills } from '../services/skillService';
import { getAllEmployees } from '../services/employeeService';
import './Skills.css';

const Skills = () => {
  const [skills, setSkills] = useState([]);
  const [employees, setEmployees] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [showForm, setShowForm] = useState(false);
  const [showAssignForm, setShowAssignForm] = useState(false);
  const [editingSkill, setEditingSkill] = useState(null);
  const [selectedEmployee, setSelectedEmployee] = useState(null);
  const [employeeSkills, setEmployeeSkills] = useState([]);
  const [loadingSkills, setLoadingSkills] = useState(false);
  const [formData, setFormData] = useState({
    skillName: '',
    description: ''
  });

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    setLoading(true);
    setError('');
    try {
      const [skillsData, employeesData] = await Promise.all([
        getAllSkills(),
        getAllEmployees()
      ]);
      setSkills(skillsData);
      setEmployees(employeesData);
    } catch (error) {
      console.error('Error fetching data:', error);
      setError(error.response?.data?.message || 'Failed to fetch data');
    } finally {
      setLoading(false);
    }
  };

  const handleShowForm = (skill = null) => {
    if (skill) {
      setEditingSkill(skill);
      setFormData({
        skillName: skill.skillName,
        description: skill.description
      });
    } else {
      setEditingSkill(null);
      setFormData({
        skillName: '',
        description: ''
      });
    }
    setShowForm(true);
  };

  const handleCloseForm = () => {
    setShowForm(false);
    setEditingSkill(null);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    // Ensure description is not null/undefined
    const submitData = {
      skillName: formData.skillName.trim(),
      description: formData.description ? formData.description.trim() : ""
    };
    
    try {
      if (editingSkill) {
        await updateSkill(editingSkill.id, submitData);
        alert('Skill updated successfully!');
      } else {
        await createSkill(submitData);
        alert('Skill created successfully!');
      }
      handleCloseForm();
      fetchData();
    } catch (error) {
      console.error('Error saving skill:', error);
      alert(error.response?.data?.message || 'Failed to save skill');
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this skill?')) {
      try {
        await deleteSkill(id);
        alert('Skill deleted successfully!');
        fetchData();
      } catch (error) {
        console.error('Error deleting skill:', error);
        alert(error.response?.data?.message || 'Failed to delete skill');
      }
    }
  };

  const handleShowAssignForm = async (employee) => {
    setSelectedEmployee(employee);
    setLoadingSkills(true);
    setShowAssignForm(true);
    try {
      const skills = await getEmployeeSkills(employee.id);
      setEmployeeSkills(skills);
    } catch (error) {
      console.error('Error fetching employee skills:', error);
    } finally {
      setLoadingSkills(false);
    }
  };

  const handleCloseAssignForm = () => {
    setShowAssignForm(false);
    setSelectedEmployee(null);
    setEmployeeSkills([]);
  };

  const handleSkillToggle = async (skillId, employeeId) => {
    try {
      const hasSkill = employeeSkills.some(skill => skill.id === skillId);
      
      if (hasSkill) {
        await removeSkillFromEmployee(employeeId, skillId);
        setEmployeeSkills(prev => prev.filter(s => s.id !== skillId));
      } else {
        await assignSkillToEmployee(employeeId, skillId);
        const skill = skills.find(s => s.id === skillId);
        if (skill) {
          setEmployeeSkills(prev => [...prev, skill]);
        }
      }
      fetchData();
    } catch (error) {
      console.error('Error toggling skill:', error);
      alert('Error updating skill assignment');
    }
  };

  return (
    <div className="skills-page">
      <div className="page-header">
        <h2>Skills</h2>
        <button className="btn btn-primary" onClick={() => handleShowForm()}>
          Add Skill
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
                <th>Skill Name</th>
                <th>Description</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {skills.map(skill => (
                <tr key={skill.id}>
                  <td>{skill.skillName}</td>
                  <td>{skill.description}</td>
                  <td>
                    <button 
                      className="btn btn-sm btn-info"
                      onClick={() => handleShowForm(skill)}
                    >
                      Edit
                    </button>
                    <button 
                      className="btn btn-sm btn-danger"
                      onClick={() => handleDelete(skill.id)}
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
            <h3>{editingSkill ? 'Edit Skill' : 'Add Skill'}</h3>
            <button onClick={handleCloseForm} style={{ background: '#dc3545', color: 'white', border: 'none', padding: '5px 10px', borderRadius: '4px', cursor: 'pointer' }}>Close</button>
          </div>
          <form onSubmit={handleSubmit}>
            <div style={{ marginBottom: '10px' }}>
              <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>Skill Name</label>
              <input
                type="text"
                name="skillName"
                value={formData.skillName}
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
            <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '10px', marginTop: '15px' }}>
              <button type="button" onClick={handleCloseForm} style={{ padding: '8px 16px', borderRadius: '4px', background: '#6c757d', color: 'white', border: 'none', cursor: 'pointer' }}>
                Cancel
              </button>
              <button type="submit" style={{ padding: '8px 16px', borderRadius: '4px', background: '#3498db', color: 'white', border: 'none', cursor: 'pointer' }}>
                {editingSkill ? 'Update' : 'Create'}
              </button>
            </div>
          </form>
        </div>
      )}

      <div style={{ marginTop: '30px' }}>
        <h3>Employee Skills Management</h3>
        <div className="table-responsive">
          <table className="table">
            <thead>
              <tr>
                <th>Employee</th>
                <th>Assigned Skills</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {employees.map(employee => (
                <tr key={employee.id}>
                  <td>{employee.firstName} {employee.lastName}</td>
                  <td>
                    {employee.skills && employee.skills.length > 0 ? (
                      employee.skills.map(skill => (
                        <span key={skill.id} className="badge badge-success" style={{ marginRight: '5px' }}>
                          {skill.skillName}
                        </span>
                      ))
                    ) : (
                      <span className="badge badge-secondary">No skills assigned</span>
                    )}
                  </td>
                  <td>
                    <button 
                      className="btn btn-sm btn-primary"
                      onClick={() => handleShowAssignForm(employee)}
                    >
                      Manage Skills
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      {showAssignForm && selectedEmployee && (
        <div style={{
          backgroundColor: 'white',
          padding: '20px',
          borderRadius: '10px',
          boxShadow: '0 4px 20px rgba(0,0,0,0.1)',
          margin: '20px 0',
          border: '2px solid #3498db'
        }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '15px' }}>
            <h3>Manage Skills - {selectedEmployee.firstName} {selectedEmployee.lastName}</h3>
            <button onClick={handleCloseAssignForm} style={{ background: '#dc3545', color: 'white', border: 'none', padding: '5px 10px', borderRadius: '4px', cursor: 'pointer' }}>Close</button>
          </div>
          <div>
            {loadingSkills ? (
              <div style={{ textAlign: 'center', padding: '20px' }}>Loading skills...</div>
            ) : (
              skills.map(skill => (
                <div key={skill.id} style={{ marginBottom: '10px', padding: '10px', border: '1px solid #ddd', borderRadius: '4px' }}>
                  <label style={{ display: 'flex', alignItems: 'center', cursor: 'pointer' }}>
                    <input
                      type="checkbox"
                      checked={employeeSkills.some(s => s.id === skill.id)}
                      onChange={() => handleSkillToggle(skill.id, selectedEmployee.id)}
                      style={{ marginRight: '10px' }}
                    />
                    <span style={{ fontWeight: 'bold' }}>{skill.skillName}</span>
                    {skill.description && <span style={{ marginLeft: '10px', color: '#666' }}>- {skill.description}</span>}
                  </label>
                </div>
              ))
            )}
          </div>
        </div>
      )}
    </div>
  );
};

export default Skills;