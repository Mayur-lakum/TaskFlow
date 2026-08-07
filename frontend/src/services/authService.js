import api from './api';
import { setToken, removeToken } from '../utils/auth';

export const login = async (credentials) => {
    const response = await api.post('/api/auth/login', credentials);
    
    // Backend returns ApiResponse<LoginResponseDTO>
    const loginData = response.data.data;

    if (loginData && loginData.token) {
        setToken(loginData.token);
    }

    return loginData;
};

export const logout = () => {
    removeToken();
};