import React, { useState, useEffect } from 'react';
import { getAllEmployees, createEmployee, updateEmployee, deleteEmployee, searchEmployees, filterEmployees, getEmployeesPage } from '../services/employeeService';
import './Employees.css';

const Employees = () => {
  const [employees, setEmployees] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [showForm, setShowForm] = useState(false);
  const [editingEmployee, setEditingEmployee] = useState(null);
  
  // Search, Filter, Pagination, Sorting state
  const [searchKeyword, setSearchKeyword] = useState('');
  const [filterDepartment, setFilterDepartment] = useState('');
  const [filterAvailability, setFilterAvailability] = useState('');
  const [currentPage, setCurrentPage] = useState(0);
  const [pageSize, setPageSize] = useState(10);
  const [totalPages, setTotalPages] = useState(0);
  const [sortBy, setSortBy] = useState('firstName');
  const [sortDirection, setSortDirection] = useState('ASC');
  const [usePagination, setUsePagination] = useState(false);
  
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    phone: '',
    department: '',
    designation: '',
    experience: '',
    availability: true
  });

  useEffect(() => {
    fetchEmployees();
  }, [searchKeyword, filterDepartment, filterAvailability, currentPage, pageSize, sortBy, sortDirection, usePagination]);

  const fetchEmployees = async () => {
    setLoading(true);
    setError('');
    try {
      let data;
      
      if (searchKeyword) {
        // Search
        data = await searchEmployees(searchKeyword);
        setEmployees(data);
        setTotalPages(0);
      } else if (filterDepartment || filterAvailability !== '') {
        // Filter
        const filters = {};
        if (filterDepartment) filters.department = filterDepartment;
        if (filterAvailability !== '') filters.availability = filterAvailability === 'true';
        data = await filterEmployees(filters);
        setEmployees(data);
        setTotalPages(0);
      } else if (usePagination) {
        // Pagination with sorting
        const pageData = await getEmployeesPage(currentPage, pageSize, sortBy, sortDirection);
        setEmployees(pageData.content || pageData);
        setTotalPages(pageData.totalPages || 0);
      } else {
        // Get all
        data = await getAllEmployees();
        setEmployees(data);
        setTotalPages(0);
      }
    } catch (error) {
      console.error('Error fetching employees:', error);
      setError(error.response?.data?.message || 'Failed to fetch employees');
    } finally {
      setLoading(false);
    }
  };

  const handleShowForm = (employee = null) => {
    if (employee) {
      setEditingEmployee(employee);
      setFormData({
        firstName: employee.firstName,
        lastName: employee.lastName,
        email: employee.email,
        phone: employee.phone || '',
        department: employee.department,
        designation: employee.designation,
        experience: employee.experience,
        availability: employee.availability
      });
    } else {
      setEditingEmployee(null);
      setFormData({
        firstName: '',
        lastName: '',
        email: '',
        phone: '',
        department: '',
        designation: '',
        experience: '',
        availability: true
      });
    }
    setShowForm(true);
  };

  const handleCloseForm = () => {
    setShowForm(false);
    setEditingEmployee(null);
  };

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    // Ensure experience is a number
    const submitData = {
      ...formData,
      experience: parseInt(formData.experience) || 0
    };
    
    try {
      if (editingEmployee) {
        await updateEmployee(editingEmployee.id, submitData);
        alert('Employee updated successfully!');
      } else {
        await createEmployee(submitData);
        alert('Employee created successfully!');
      }
      handleCloseForm();
      fetchEmployees();
    } catch (error) {
      console.error('Error saving employee:', error);
      alert(error.response?.data?.message || 'Failed to save employee');
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this employee?')) {
      try {
        await deleteEmployee(id);
        alert('Employee deleted successfully!');
        fetchEmployees();
      } catch (error) {
        console.error('Error deleting employee:', error);
        alert(error.response?.data?.message || 'Failed to delete employee');
      }
    }
  };

  const handleSearch = (e) => {
    setSearchKeyword(e.target.value);
    setCurrentPage(0);
    setUsePagination(false);
  };

  const handleFilterChange = (field, value) => {
    if (field === 'department') {
      setFilterDepartment(value);
    } else if (field === 'availability') {
      setFilterAvailability(value);
    }
    setCurrentPage(0);
    setUsePagination(false);
  };

  const handleClearFilters = () => {
    setSearchKeyword('');
    setFilterDepartment('');
    setFilterAvailability('');
    setCurrentPage(0);
    setUsePagination(false);
  };

  const handlePageChange = (newPage) => {
    setCurrentPage(newPage);
  };

  const handleSortChange = (field, value) => {
    if (field === 'sortBy') {
      setSortBy(value);
    } else if (field === 'direction') {
      setSortDirection(value);
    }
    setCurrentPage(0);
  };

  const togglePaginationMode = () => {
    setUsePagination(!usePagination);
    setCurrentPage(0);
  };

  return (
    <div className="employees-page">
      <div className="page-header">
        <h2>Employees</h2>
        <button className="btn btn-primary" onClick={() => handleShowForm()}>
          Add Employee
        </button>
      </div>

      {/* Search, Filter, Sort, Pagination Controls */}
      <div style={{ 
        backgroundColor: 'white', 
        padding: '15px', 
        borderRadius: '8px', 
        marginBottom: '20px', 
        boxShadow: '0 2px 4px rgba(0,0,0,0.1)' 
      }}>
        <div style={{ display: 'flex', flexWrap: 'wrap', gap: '15px', alignItems: 'flex-end' }}>
          {/* Search */}
          <div style={{ flex: '1', minWidth: '200px' }}>
            <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold', fontSize: '14px' }}>Search</label>
            <input
              type="text"
              placeholder="Search by name..."
              value={searchKeyword}
              onChange={handleSearch}
              style={{ width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px' }}
            />
          </div>

          {/* Filter Department */}
          <div style={{ minWidth: '150px' }}>
            <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold', fontSize: '14px' }}>Department</label>
            <select
              value={filterDepartment}
              onChange={(e) => handleFilterChange('department', e.target.value)}
              style={{ width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px' }}
            >
              <option value="">All Departments</option>
              <option value="IT">IT</option>
              <option value="HR">HR</option>
              <option value="Finance">Finance</option>
              <option value="Marketing">Marketing</option>
              <option value="Operations">Operations</option>
            </select>
          </div>

          {/* Filter Availability */}
          <div style={{ minWidth: '150px' }}>
            <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold', fontSize: '14px' }}>Availability</label>
            <select
              value={filterAvailability}
              onChange={(e) => handleFilterChange('availability', e.target.value)}
              style={{ width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px' }}
            >
              <option value="">All</option>
              <option value="true">Available</option>
              <option value="false">Not Available</option>
            </select>
          </div>

          {/* Sort By */}
          <div style={{ minWidth: '150px' }}>
            <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold', fontSize: '14px' }}>Sort By</label>
            <select
              value={sortBy}
              onChange={(e) => handleSortChange('sortBy', e.target.value)}
              style={{ width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px' }}
            >
              <option value="firstName">Name</option>
              <option value="experience">Experience</option>
              <option value="department">Department</option>
              <option value="designation">Designation</option>
            </select>
          </div>

          {/* Sort Direction */}
          <div style={{ minWidth: '120px' }}>
            <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold', fontSize: '14px' }}>Order</label>
            <select
              value={sortDirection}
              onChange={(e) => handleSortChange('direction', e.target.value)}
              style={{ width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px' }}
            >
              <option value="ASC">Ascending</option>
              <option value="DESC">Descending</option>
            </select>
          </div>

          {/* Clear Filters */}
          <div>
            <button
              onClick={handleClearFilters}
              className="btn btn-secondary"
              style={{ padding: '8px 16px' }}
            >
              Clear Filters
            </button>
          </div>

          {/* Toggle Pagination */}
          <div>
            <button
              onClick={togglePaginationMode}
              className="btn btn-info"
              style={{ padding: '8px 16px' }}
            >
              {usePagination ? 'Show All' : 'Use Pagination'}
            </button>
          </div>
        </div>

        {/* Pagination Controls */}
        {usePagination && totalPages > 0 && (
          <div style={{ 
            display: 'flex', 
            justifyContent: 'space-between', 
            alignItems: 'center', 
            marginTop: '15px', 
            paddingTop: '15px', 
            borderTop: '1px solid #eee' 
          }}>
            <div style={{ fontSize: '14px', color: '#666' }}>
              Page {currentPage + 1} of {totalPages}
            </div>
            <div style={{ display: 'flex', gap: '10px' }}>
              <button
                onClick={() => handlePageChange(0)}
                disabled={currentPage === 0}
                className="btn btn-sm btn-secondary"
              >
                First
              </button>
              <button
                onClick={() => handlePageChange(currentPage - 1)}
                disabled={currentPage === 0}
                className="btn btn-sm btn-secondary"
              >
                Previous
              </button>
              <button
                onClick={() => handlePageChange(currentPage + 1)}
                disabled={currentPage >= totalPages - 1}
                className="btn btn-sm btn-secondary"
              >
                Next
              </button>
              <button
                onClick={() => handlePageChange(totalPages - 1)}
                disabled={currentPage >= totalPages - 1}
                className="btn btn-sm btn-secondary"
              >
                Last
              </button>
            </div>
          </div>
        )}
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
                <th>Name</th>
                <th>Email</th>
                <th>Department</th>
                <th>Designation</th>
                <th>Experience</th>
                <th>Skills</th>
                <th>Availability</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {employees.map(employee => (
                <tr key={employee.id}>
                  <td>{employee.firstName} {employee.lastName}</td>
                  <td>{employee.email}</td>
                  <td>{employee.department}</td>
                  <td>{employee.designation}</td>
                  <td>{employee.experience} years</td>
                  <td>
                    {employee.skills && employee.skills.length > 0 ? (
                      <div>
                        {employee.skills.slice(0, 3).map(skill => (
                          <span key={skill.id} className="badge badge-success" style={{ marginRight: '3px', fontSize: '11px' }}>
                            {skill.skillName}
                          </span>
                        ))}
                        {employee.skills.length > 3 && (
                          <span className="badge badge-secondary" style={{ fontSize: '11px' }}>
                            +{employee.skills.length - 3}
                          </span>
                        )}
                      </div>
                    ) : (
                      <span className="badge badge-secondary" style={{ fontSize: '11px' }}>No skills</span>
                    )}
                  </td>
                  <td>
                    <span className={`badge ${employee.availability ? 'badge-success' : 'badge-secondary'}`}>
                      {employee.availability ? 'Available' : 'Not Available'}
                    </span>
                  </td>
                  <td>
                    <button 
                      className="btn btn-sm btn-info"
                      onClick={() => handleShowForm(employee)}
                    >
                      Edit
                    </button>
                    <button 
                      className="btn btn-sm btn-danger"
                      onClick={() => handleDelete(employee.id)}
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
            <h3>{editingEmployee ? 'Edit Employee' : 'Add Employee'}</h3>
            <button onClick={handleCloseForm} style={{ background: '#dc3545', color: 'white', border: 'none', padding: '5px 10px', borderRadius: '4px', cursor: 'pointer' }}>Close</button>
          </div>
          <form onSubmit={handleSubmit}>
            <div style={{ marginBottom: '10px' }}>
              <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>First Name</label>
              <input
                type="text"
                name="firstName"
                value={formData.firstName}
                onChange={handleChange}
                style={{ width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px' }}
                required
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>Last Name</label>
              <input
                type="text"
                name="lastName"
                value={formData.lastName}
                onChange={handleChange}
                style={{ width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px' }}
                required
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>Email</label>
              <input
                type="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                style={{ width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px' }}
                required
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>Phone *</label>
              <input
                type="text"
                name="phone"
                value={formData.phone}
                onChange={handleChange}
                style={{ width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px' }}
                required
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>Department</label>
              <input
                type="text"
                name="department"
                value={formData.department}
                onChange={handleChange}
                style={{ width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px' }}
                required
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>Designation</label>
              <input
                type="text"
                name="designation"
                value={formData.designation}
                onChange={handleChange}
                style={{ width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px' }}
                required
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>Experience (years)</label>
              <input
                type="number"
                name="experience"
                value={formData.experience}
                onChange={handleChange}
                style={{ width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px' }}
                required
                min="0"
                step="1"
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>
                <input
                  type="checkbox"
                  name="availability"
                  checked={formData.availability}
                  onChange={handleChange}
                  style={{ marginRight: '8px' }}
                />
                Available
              </label>
            </div>
            <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '10px', marginTop: '15px' }}>
              <button type="button" onClick={handleCloseForm} style={{ padding: '8px 16px', borderRadius: '4px', background: '#6c757d', color: 'white', border: 'none', cursor: 'pointer' }}>
                Cancel
              </button>
              <button type="submit" style={{ padding: '8px 16px', borderRadius: '4px', background: '#3498db', color: 'white', border: 'none', cursor: 'pointer' }}>
                {editingEmployee ? 'Update' : 'Create'}
              </button>
            </div>
          </form>
        </div>
      )}
    </div>
  );
};

export default Employees;