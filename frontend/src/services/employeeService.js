import api from './api';

export const getAllEmployees = async () => {
  const response = await api.get('/api/v1/employees');
  return response.data.data;
};

export const getEmployeeById = async (id) => {
  const response = await api.get(`/api/v1/employees/${id}`);
  return response.data.data;
};

export const createEmployee = async (employee) => {
  const response = await api.post('/api/v1/employees', employee);
  return response.data.data;
};

export const updateEmployee = async (id, employee) => {
  const response = await api.put(`/api/v1/employees/${id}`, employee);
  return response.data.data;
};

export const deleteEmployee = async (id) => {
  await api.delete(`/api/v1/employees/${id}`);
};

export const searchEmployees = async (keyword) => {
  const response = await api.get(`/api/v1/employees/search?keyword=${keyword}`);
  return response.data.data;
};

export const filterEmployees = async (filters) => {
  const params = new URLSearchParams();
  if (filters.department) params.append('department', filters.department);
  if (filters.availability !== undefined) params.append('availability', filters.availability);
  const response = await api.get(`/api/v1/employees/filter?${params.toString()}`);
  return response.data.data;
};

export const getEmployeesPage = async (page, size, sortBy, direction) => {
  const params = new URLSearchParams();
  params.append('page', page);
  params.append('size', size);
  if (sortBy) params.append('sortBy', sortBy);
  if (direction) params.append('direction', direction);
  const response = await api.get(`/api/v1/employees/page?${params.toString()}`);
  return response.data.data;
};