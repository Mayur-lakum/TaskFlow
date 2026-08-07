# TaskFlow AI - Frontend

Intelligent Employee Recommendation System - Frontend Application

## Overview

TaskFlow AI is an employee and project management system with an intelligent recommendation engine. This frontend application provides a professional dashboard for managing employees, projects, skills, and getting AI-powered employee recommendations for projects.

## Technology Stack

- **React** (Vite) - UI Framework
- **React Router** - Client-side routing
- **Axios** - HTTP client for API requests
- **Bootstrap** - CSS framework for styling
- **JWT** - Authentication tokens

## Features

### Authentication
- JWT-based login system
- Protected routes for authenticated users
- Automatic token management and refresh

### Dashboard
- Statistics cards showing total employees, projects, skills, and available employees
- Recent employees and projects overview
- Quick access to recommendation feature

### Employee Management
- Full CRUD operations (Create, Read, Update, Delete)
- Search employees by name, email, department, designation
- Filter by department and availability status
- Sort by experience, department, and name
- Pagination support
- Professional table layout with status badges

### Project Management
- Full CRUD operations
- Project tracking with status indicators
- Client information management
- Required experience specification

### Skill Management
- Full CRUD operations for skills
- Employee skill assignment system
- Skill removal from employees
- View employee skill profiles

### Recommendation Engine
- Select project and get AI-powered recommendations
- Match score calculation and display
- Employee ranking system
- Matched skills visualization
- Top recommendation highlighting

## API Integration

The frontend consumes the following backend APIs:

### Authentication
- `POST /api/auth/login` - User authentication

### Employees
- `GET /api/v1/employees` - Get all employees
- `GET /api/v1/employees/{id}` - Get employee by ID
- `POST /api/v1/employees` - Create new employee
- `PUT /api/v1/employees/{id}` - Update employee
- `DELETE /api/v1/employees/{id}` - Delete employee
- `GET /api/v1/employees/search?keyword=xxx` - Search employees
- `GET /api/v1/employees/filter?department=xxx&availability=xxx` - Filter employees
- `GET /api/v1/employees/page?page=0&size=5&sortBy=xxx&direction=xxx` - Paginated employees

### Projects
- `GET /api/v1/projects` - Get all projects
- `GET /api/v1/projects/{id}` - Get project by ID
- `POST /api/v1/projects` - Create new project
- `PUT /api/v1/projects/{id}` - Update project
- `DELETE /api/v1/projects/{id}` - Delete project

### Skills
- `GET /api/v1/skills` - Get all skills
- `GET /api/v1/skills/{id}` - Get skill by ID
- `POST /api/v1/skills` - Create new skill
- `PUT /api/v1/skills/{id}` - Update skill
- `DELETE /api/v1/skills/{id}` - Delete skill
- `POST /api/v1/employees/{employeeId}/skills/{skillId}` - Assign skill to employee
- `DELETE /api/v1/employees/{employeeId}/skills/{skillId}` - Remove skill from employee
- `GET /api/v1/employees/{employeeId}/skills` - Get employee skills

### Recommendations
- `GET /api/v1/recommendations/{projectId}` - Get employee recommendations for project

## Installation

1. **Navigate to the project directory:**
   ```bash
   cd taskflowai-ui
   ```

2. **Install dependencies:**
   ```bash
   npm install
   ```

3. **Start the development server:**
   ```bash
   npm run dev
   ```

4. **Open your browser:**
   Navigate to `http://localhost:5173`

## Configuration

### API Base URL

The API base URL is configured in `src/services/api.js`:

```javascript
const API_BASE_URL = 'http://localhost:8080';
```

Make sure your backend is running on `http://localhost:8080` or update this URL accordingly.

## Project Structure

```
taskflowai-ui/
├── src/
│   ├── components/       # Reusable components
│   ├── pages/           # Page components
│   │   ├── Login.jsx
│   │   ├── Dashboard.jsx
│   │   ├── Employees.jsx
│   │   ├── Projects.jsx
│   │   ├── Skills.jsx
│   │   └── Recommendations.jsx
│   ├── services/        # API service layers
│   │   ├── api.js
│   │   ├── authService.js
│   │   ├── employeeService.js
│   │   ├── projectService.js
│   │   ├── skillService.js
│   │   └── recommendationService.js
│   ├── layouts/         # Layout components
│   │   └── Layout.jsx
│   ├── router/          # Router configuration
│   │   └── index.jsx
│   ├── utils/           # Utility functions
│   │   └── auth.js
│   ├── App.jsx          # Main App component
│   ├── App.css          # Global styles
│   └── main.jsx         # Entry point
├── package.json
└── vite.config.js
```

## Usage

### Login

1. Navigate to the login page
2. Enter your username and password
3. Click "Login" to authenticate
4. You will be redirected to the dashboard

### Dashboard

- View overall statistics
- See recent employees and projects
- Click "Quick Recommendation" to access the recommendation feature

### Employee Management

1. Navigate to "Employees" from the sidebar
2. Use search, filter, and sort options to find employees
3. Click "Add Employee" to create a new employee
4. Click "Edit" to modify employee details
5. Click "Delete" to remove an employee
6. Use pagination to navigate through large datasets

### Project Management

1. Navigate to "Projects" from the sidebar
2. Click "Add Project" to create a new project
3. Click "Edit" to modify project details
4. Click "Delete" to remove a project

### Skill Management

1. Navigate to "Skills" from the sidebar
2. Click "Add Skill" to create a new skill
3. Click "Manage Skills" next to an employee to assign/remove skills
4. Click "Edit" to modify skill details
5. Click "Delete" to remove a skill

### Recommendations

1. Navigate to "Recommendations" from the sidebar
2. Select a project from the dropdown
3. Click "Recommend Team" to get AI-powered recommendations
4. View ranked employees with match scores and matched skills
5. The top recommendation is highlighted

## Design Features

- **Professional Corporate Design**: Clean, business-appropriate interface
- **Responsive Layout**: Works on desktop and laptop screens
- **Color Scheme**: Blue, white, and gray color palette
- **Status Indicators**: Visual badges for availability and project status
- **Smooth Interactions**: Hover effects and transitions
- **Loading States**: User feedback during API calls
- **Error Handling**: Graceful error messages and alerts

## Security Features

- JWT token-based authentication
- Protected routes for authenticated users
- Automatic token attachment to API requests
- Token refresh and 401 error handling
- Secure token storage in localStorage

## Browser Support

- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)

## Development

### Build for Production

```bash
npm run build
```

### Preview Production Build

```bash
npm run preview
```

### Lint Code

```bash
npm run lint
```

## Important Notes

- This frontend is designed to work with the existing TaskFlow AI backend
- Do not modify backend API endpoints without updating the frontend
- The backend must be running on `http://localhost:8080` for the frontend to work
- JWT tokens are stored in localStorage for session management
- All API requests automatically include the Authorization header

## Troubleshooting

### Backend Connection Issues

If you encounter connection errors:
1. Ensure the backend is running on `http://localhost:8080`
2. Check that CORS is properly configured on the backend
3. Verify the API base URL in `src/services/api.js`

### Authentication Issues

If you experience login problems:
1. Check your backend authentication endpoint
2. Verify JWT token generation in the backend
3. Clear browser localStorage and try again

### Build Errors

If you encounter build errors:
1. Delete `node_modules` and `package-lock.json`
2. Run `npm install` again
3. Ensure all dependencies are properly installed

## License

This project is part of TaskFlow AI - Employee Recommendation System

## Support

For issues and questions related to the backend API, please refer to the backend documentation or contact the backend development team.