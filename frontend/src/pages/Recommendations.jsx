import React, { useState, useEffect } from 'react';
import { getAllProjects } from '../services/projectService';
import { getRecommendations } from '../services/recommendationService';
import './Recommendations.css';

const Recommendations = () => {
  const [projects, setProjects] = useState([]);
  const [selectedProject, setSelectedProject] = useState('');
  const [recommendations, setRecommendations] = useState([]);
  const [loading, setLoading] = useState(false);
  const [loadingProjects, setLoadingProjects] = useState(true);
  const [error, setError] = useState('');
  const [hasSearched, setHasSearched] = useState(false);

  useEffect(() => {
    fetchProjects();
  }, []);

  const fetchProjects = async () => {
    setLoadingProjects(true);
    setError('');
    try {
      const response = await getAllProjects();
      setProjects(response);
    } catch (error) {
      console.error('Error fetching projects:', error);
      setError(error.response?.data?.message || 'Failed to fetch projects');
    } finally {
      setLoadingProjects(false);
    }
  };

  const handleProjectChange = (e) => {
    setSelectedProject(e.target.value);
    setRecommendations([]);
    setHasSearched(false);
  };

  const handleRecommend = async () => {
    if (!selectedProject) return;

    setLoading(true);
    setHasSearched(true);
    
    try {
      const response = await getRecommendations(selectedProject);
      setRecommendations(response);
    } catch (error) {
      console.error('Error fetching recommendations:', error);
      setError(error.response?.data?.message || 'Failed to fetch recommendations');
      setRecommendations([]);
    } finally {
      setLoading(false);
    }
  };

  const getScoreColor = (score) => {
    if (score >= 80) return '#2ecc71';
    if (score >= 60) return '#3498db';
    if (score >= 40) return '#f39c12';
    return '#e74c3c';
  };

  const getRankBadge = (rank) => {
    if (rank === 1) return '🥇';
    if (rank === 2) return '🥈';
    if (rank === 3) return '🥉';
    return `#${rank}`;
  };

  return (
    <div className="recommendations-page">
      <div className="page-header">
        <h2>Employee Recommendations</h2>
      </div>

      {error && (
        <div className="alert alert-danger" style={{ marginBottom: '20px' }}>{error}</div>
      )}

      {loadingProjects ? (
        <div className="text-center">Loading projects...</div>
      ) : (
        <div className="recommendation-form">
        <div className="form-group">
          <label htmlFor="projectSelect">Select Project</label>
          <select
            id="projectSelect"
            value={selectedProject}
            onChange={handleProjectChange}
            className="form-control"
          >
            <option value="">Choose a project...</option>
            {projects.map(project => (
              <option key={project.id} value={project.id}>
                {project.projectCode} - {project.projectName}
              </option>
            ))}
          </select>
        </div>
        
        <button 
          onClick={handleRecommend}
          className="btn btn-primary btn-recommend"
          disabled={!selectedProject || loading}
        >
          {loading ? 'Analyzing...' : 'Recommend Team'}
        </button>
        </div>
      )}

      {hasSearched && (
        <div className="recommendations-results">
          {loading ? (
            <div className="text-center">
              <div className="spinner"></div>
              <p>Analyzing employee skills and project requirements...</p>
            </div>
          ) : recommendations.length === 0 ? (
            <div className="no-results">
              <p>No recommendations found for this project.</p>
            </div>
          ) : (
            <>
              <div className="results-header">
                <h3>Top Recommendations</h3>
                <p className="results-count">
                  Found {recommendations.length} qualified employee(s)
                </p>
              </div>

              <div className="recommendations-list">
                {recommendations.map((rec, index) => (
                  <div 
                    key={rec.employeeId || index} 
                    className={`recommendation-card ${index === 0 ? 'top-recommendation' : ''}`}
                  >
                    <div className="recommendation-header">
                      <div className="rank-badge">
                        {getRankBadge(index + 1)}
                      </div>
                      <div className="match-score">
                        <div 
                          className="score-circle"
                          style={{ 
                            borderColor: getScoreColor(rec.score),
                            color: getScoreColor(rec.score)
                          }}
                        >
                          {rec.score}%
                        </div>
                        <span className="score-label">Match Score</span>
                      </div>
                    </div>

                    <div className="employee-details">
                      <h4 className="employee-name">
                        {rec.employeeName}
                      </h4>
                      <div className="employee-info-grid">
                        <div className="info-row">
                          <span className="info-label">Employee Code:</span>
                          <span className="info-value">{rec.employeeCode}</span>
                        </div>
                        <div className="info-row">
                          <span className="info-label">Department:</span>
                          <span className="info-value">{rec.department}</span>
                        </div>
                        <div className="info-row">
                          <span className="info-label">Experience:</span>
                          <span className="info-value">{rec.experience} years</span>
                        </div>
                        <div className="info-row">
                          <span className="info-label">Matched Skills:</span>
                          <span className="info-value badge badge-success">{rec.matchedSkills}</span>
                        </div>
                        <div className="info-row">
                          <span className="info-label">Final Score:</span>
                          <span className="info-value" style={{ color: getScoreColor(rec.score), fontWeight: 'bold' }}>{rec.score}%</span>
                        </div>
                      </div>
                    </div>

                    {index === 0 && (
                      <div className="top-choice-badge">
                        ⭐ Top Recommendation
                      </div>
                    )}
                  </div>
                ))}
              </div>
            </>
          )}
        </div>
      )}
    </div>
  );
};

export default Recommendations;