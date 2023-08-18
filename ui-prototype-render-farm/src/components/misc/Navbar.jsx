import React from 'react'
import { useNavigate } from 'react-router-dom'
import { useKeycloak } from '@react-keycloak/web'
import { NavLink } from 'react-router-dom'
import { Container, Dropdown, Menu } from 'semantic-ui-react'
import { isAdmin } from '../misc/Helpers'

function Navbar() {
  const { keycloak } = useKeycloak()
  const navigate = useNavigate()

  const handleLogInOut = () => {
    if (keycloak.authenticated) {
      navigate('/')
      keycloak.logout()
    } else {
      keycloak.login()
    }
  }

  const checkAuthenticated = () => {
    if (!keycloak.authenticated) {
      handleLogInOut()
    }
  }

  const getUsername = () => {
    return keycloak.authenticated && keycloak.tokenParsed?.preferred_username
  }

  const getLogInOutText = () => {
    return keycloak.authenticated ? 'Logout' : 'Login'
  }

  const getAdminMenuStyle = () => {
    return keycloak.authenticated && isAdmin(keycloak)
      ? { display: 'block' }
      : { display: 'none' }
  }

  return (
    <Menu stackable>
      <Container>
        <Menu.Item header>Tasks UI</Menu.Item>
        <Menu.Item as={NavLink} exact to="/home">
          Home
        </Menu.Item>
        <Dropdown item text="Admin" style={getAdminMenuStyle()}>
          <Dropdown.Menu>
            <Dropdown.Item
              as={NavLink}
              exact
              to="/tasks"
              onClick={checkAuthenticated}
            >
              Tasks
            </Dropdown.Item>
          </Dropdown.Menu>
        </Dropdown>
        <Menu.Menu position="right">
          {keycloak.authenticated && (
            <Dropdown
              text={`Hi ${getUsername()}`}
              pointing
              className="link item"
            >
              <Dropdown.Menu>
                <Dropdown.Item as={NavLink} to="/settings">
                  Settings
                </Dropdown.Item>
              </Dropdown.Menu>
            </Dropdown>
          )}
          <Menu.Item as={NavLink} exact to="/login" onClick={handleLogInOut}>
            {getLogInOutText()}
          </Menu.Item>
        </Menu.Menu>
      </Container>
    </Menu>
  )
}

export default Navbar
