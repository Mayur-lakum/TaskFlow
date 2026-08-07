import api from './api';

export const getAllProjects = async () => {
  const response = await api.get('/api/v1/projects');
  return response.data.data;
};

export const getProjectById = async (id) => {
  const response = await api.get(`/api/v1/projects/${id}`);
  return response.data.data;
};

export const createProject = async (project) => {
  console.log('Creating project with data:', JSON.stringify(project, null, 2));
  const response = await api.post('/api/v1/projects', project);
  console.log('Backend response:', response.data);
  return response.data.data;
};

export const updateProject = async (id, project) => {
  const response = await api.put(`/api/v1/projects/${id}`, project);
  return response.data.data;
};

export const deleteProject = async (id) => {
  await api.delete(`/api/v1/projects/${id}`);
};