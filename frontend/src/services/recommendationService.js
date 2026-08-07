import api from './api';

export const getRecommendations = async (projectId) => {
  const response = await api.get(`/api/v1/recommendations/${projectId}`);
  return response.data.data;
};