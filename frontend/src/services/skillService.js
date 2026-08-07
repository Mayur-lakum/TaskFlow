import api from './api';

export const getAllSkills = async () => {
  const response = await api.get('/api/v1/skills');
  return response.data.data;
};

export const getSkillById = async (id) => {
  const response = await api.get(`/api/v1/skills/${id}`);
  return response.data.data;
};

export const createSkill = async (skill) => {
  const response = await api.post('/api/v1/skills', skill);
  return response.data.data;
};

export const updateSkill = async (id, skill) => {
  const response = await api.put(`/api/v1/skills/${id}`, skill);
  return response.data.data;
};

export const deleteSkill = async (id) => {
  await api.delete(`/api/v1/skills/${id}`);
};

export const assignSkillToEmployee = async (employeeId, skillId) => {
  await api.post(`/api/v1/employees/${employeeId}/skills/${skillId}`);
};

export const removeSkillFromEmployee = async (employeeId, skillId) => {
  await api.delete(`/api/v1/employees/${employeeId}/skills/${skillId}`);
};

export const getEmployeeSkills = async (employeeId) => {
  const response = await api.get(`/api/v1/employees/${employeeId}/skills`);
  return response.data.data;
};