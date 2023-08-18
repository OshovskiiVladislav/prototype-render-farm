import axios from 'axios'
import { config } from '../../Constants'

export const tasksApi = {
  getTasks,
  getTask,
  saveTask,
  deleteTask,
  getUserExtrasMe,
  saveUserExtrasMe,
}

function getTasks(token) {
  return instance.get('/api/v1/tasks/username', {
    headers: { Authorization: bearerAuth(token) },
  })
}

function getTask(id, token) {
  return instance.get(`/api/v1/tasks/${id}`, {
    headers: { Authorization: bearerAuth(token) },
  })
}

function deleteTask(id, token) {
  return instance.delete(`/api/v1/tasks/${id}`, {
    headers: { Authorization: bearerAuth(token) },
  })
}

function saveTask(taskDto, token) {
  return instance.post('/api/v1/tasks', taskDto, {
    headers: { Authorization: bearerAuth(token) },
  })
}

function getUserExtrasMe(token) {
  return instance.get(`/api/v1/userextras/me`, {
    headers: { Authorization: bearerAuth(token) },
  })
}

function saveUserExtrasMe(token, userExtra) {
  return instance.post(`/api/v1/userextras/me`, userExtra, {
    headers: { Authorization: bearerAuth(token) },
  })
}

// -- Axios

const instance = axios.create({
  baseURL: config.url.API_BASE_URL,
})

instance.interceptors.response.use(
  (response) => {
    return response
  },
  function (error) {
    if (error.response.status === 404) {
      return { status: error.response.status }
    }
    return Promise.reject(error.response)
  }
)
// -- Helper functions

function bearerAuth(token) {
  return `Bearer ${token}`
}
