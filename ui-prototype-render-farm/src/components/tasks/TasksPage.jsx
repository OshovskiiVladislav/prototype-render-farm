import React, { useEffect, useState } from 'react'
import {
  Container,
  Grid,
  Header,
  Segment,
  Icon,
  Divider,
} from 'semantic-ui-react'
import { useKeycloak } from '@react-keycloak/web'
import { handleLogError } from '../misc/Helpers'
import { isAdmin } from '../misc/Helpers'
import { Navigate } from 'react-router-dom'
import ConfirmationModal from '../misc/ConfirmationModal'
import { tasksApi } from '../misc/TaskApi'
import TasksForm from './TasksForm'
import TasksTable from './TasksTable'

const formInitialState = {
  id: '',
  title: '',
  type: '',

  titleError: false,
  typeError: false,
}

const modalInitialState = {
  isOpen: false,
  header: '',
  content: '',
  onAction: null,
  onClose: null,
}

function TasksPage() {
  const [tasks, setTasks] = useState([])
  const [form, setForm] = useState({ ...formInitialState })
  const [modal, setModal] = useState({ ...modalInitialState })
  const [taskToBeDeleted, setTaskToBeDeleted] = useState(null)

  const { keycloak } = useKeycloak()

  useEffect(() => {
    handleGetTasks()
  }, [])

  const handleChange = (e) => {
    const { id, value } = e.target
    setForm((prevForm) => ({ ...prevForm, [id]: value }))
  }

  const handleGetTasks = async () => {
    try {
      const response = await tasksApi.getTasks(keycloak.token)
      const tasks = response.data
      console.log(
        ' handleGetTasks handleGetTasks handleGetTasks ' + JSON.stringify(tasks)
      )
      setTasks(tasks)
    } catch (error) {
      handleLogError(error)
    }
  }

  const handleSaveTask = async () => {
    if (!isValidForm()) {
      return
    }
    const { title, type } = form
    const task = { title, type }
    try {
      await tasksApi.saveTask(task, keycloak.token)
      clearForm()
      handleGetTasks()
    } catch (error) {
      handleLogError(error)
    }
  }

  const handleDeleteTask = (task) => {
    const modal = {
      isOpen: true,
      header: 'Delete Task',
      content: `Would you like to delete task '${task.title}'?`,
      onAction: handleActionModal,
      onClose: handleCloseModal,
    }
    console.log(
      'handleDeleteTaskhandleDeleteTaskhandleDeleteTask' + JSON.stringify(task)
    )
    setTaskToBeDeleted(task)
    setModal(modal)
    // The deletion is done in handleActionModal function
  }

  const handleEditTask = (task) => {
    const form = {
      id: task.id,
      title: task.title,
      type: task.type,
    }
    setForm(form)
  }

  const clearForm = () => {
    setForm({ ...formInitialState })
  }

  const isValidForm = () => {
    const titleError = form.title.trim() === ''
    const typeError = form.type.trim() === ''

    setForm((prevForm) => ({
      ...prevForm,
      titleError,
      typeError,
    }))

    return !(titleError || typeError)
  }

  const handleActionModal = async (response, task) => {
    if (response) {
      try {
        console.log(
          'responseresponseresponseresponseresponseresponseresponseresponseresponse' +
            response
        )
        console.log(task)

        await tasksApi.deleteTask(task.id, keycloak.token)
        handleGetTasks()
      } catch (error) {
        handleLogError(error)
      }
    }
    handleCloseModal()
  }

  const handleCloseModal = () => {
    setModal({ ...modalInitialState })
    setTaskToBeDeleted(null)
  }

  if (!isAdmin(keycloak)) {
    return <Navigate to="/" />
  }

  return (
    <Container>
      <Grid>
        <Grid.Column mobile={16} tablet={16} computer={4}>
          <Segment>
            <Header as="h2">
              <Icon name="book icon" />
              <Header.Content>Tasks</Header.Content>
            </Header>
            <Divider />
            <TasksForm
              form={form}
              handleChange={handleChange}
              handleSaveTask={handleSaveTask}
              clearForm={clearForm}
            />
          </Segment>
        </Grid.Column>
        <Grid.Column mobile={16} tablet={16} computer={12}>
          <TasksTable
            tasks={tasks}
            handleDeleteTask={handleDeleteTask}
            handleEditTask={handleEditTask}
          />
        </Grid.Column>
      </Grid>

      <ConfirmationModal modal={modal} task={taskToBeDeleted} />
    </Container>
  )
}

export default TasksPage
