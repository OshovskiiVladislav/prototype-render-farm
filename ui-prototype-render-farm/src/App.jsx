import './App.css'
import React from 'react'
import { Dimmer, Header, Icon } from 'semantic-ui-react'
import { ReactKeycloakProvider } from '@react-keycloak/web'
import {
  BrowserRouter as Router,
  Route,
  Routes,
  Navigate,
} from 'react-router-dom'
import { tasksApi } from './components/misc/TaskApi'
import Home from './components/home/Home'
import Navbar from './components/misc/Navbar'
import keycloak from './components/security/keycloak'
import UserSettings from './components/settings/UserSettings'
import PrivateRoute from './components/misc/PrivateRoute'
import TasksPage from './components/tasks/TasksPage'
import TaskDetail from './components/tasks/TaskDetail'

function App() {
  const initOptions = { pkceMethod: 'S256' }

  const handleOnEvent = async (event, error) => {
    if (event === 'onAuthSuccess') {
      if (keycloak.authenticated) {
        console.log(keycloak.authenticated)
        let response = await tasksApi.getUserExtrasMe(keycloak.token)
        console.log(response)
        if (response.status === 404) {
          const userExtra = { avatar: keycloak.tokenParsed.preferred_username }
          console.log(userExtra)
          response = await tasksApi.saveUserExtrasMe(keycloak.token, userExtra)
          console.log(response)
          console.log(
            'UserExtra created for ' + keycloak.tokenParsed.preferred_username
          )
        }
        keycloak['avatar'] = response.data.avatar
      }
    }
  }

  const loadingComponent = (
    <Dimmer inverted active={true} page>
      <Header style={{ color: '#4d4d4d' }} as="h2" icon inverted>
        <Icon loading name="cog" />
        <Header.Content>
          Keycloak is loading
          <Header.Subheader style={{ color: '#4d4d4d' }}>
            or running authorization code flow with PKCE
          </Header.Subheader>
        </Header.Content>
      </Header>
    </Dimmer>
  )

  return (
    <ReactKeycloakProvider
      authClient={keycloak}
      initOptions={initOptions}
      LoadingComponent={loadingComponent}
      onEvent={(event, error) => handleOnEvent(event, error)}
    >
      <Router>
        <Navbar />
        <Routes>
          <Route path="/" element={<Home />} exact />
          <Route path="/home" element={<Home />} />
          <Route element={<Home />} />
          <Route path="/tasks/:id" element={<TaskDetail />} />
          <Route
            path="/tasks"
            element={
              <PrivateRoute>
                <TasksPage />
              </PrivateRoute>
            }
          />
          <Route
            path="/settings"
            element={
              <PrivateRoute>
                <UserSettings />
              </PrivateRoute>
            }
          />
          <Route path="*" element={<Navigate to="/" />} />
        </Routes>
      </Router>
    </ReactKeycloakProvider>
  )
}

export default App
