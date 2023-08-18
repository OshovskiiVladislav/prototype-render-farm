import React from 'react'
import { Button, Image, Table } from 'semantic-ui-react'

function TasksTable({ tasks, handleDeleteTask, handleEditTask }) {
  const height = window.innerHeight - 100
  const style = {
    height: height,
    maxHeight: height,
    overflowY: 'auto',
    overflowX: 'hidden',
  }

  const taskList =
    tasks &&
    tasks.map((task) => {
      return (
        <Table.Row key={task.id}>
          <Table.Cell collapsing>
            <Button
              circular
              color="red"
              size="small"
              icon="trash"
              onClick={() => handleDeleteTask(task)}
            />
            <Button
              circular
              color="orange"
              size="small"
              icon="edit"
              onClick={() => handleEditTask(task)}
            />
          </Table.Cell>
          <Table.Cell>{task.id}</Table.Cell>
          <Table.Cell>{task.title}</Table.Cell>
          <Table.Cell>{task.type}</Table.Cell>
          <Table.Cell>
            <Image
              size="tiny"
              src={task.poster ? task.poster : '/images/task-title.png'}
              rounded
            />
          </Table.Cell>
        </Table.Row>
      )
    })

  return (
    <div style={style}>
      <Table compact striped>
        <Table.Header>
          <Table.Row>
            <Table.HeaderCell width={2} />
            <Table.HeaderCell width={2}>id</Table.HeaderCell>
            <Table.HeaderCell width={4}>Title</Table.HeaderCell>
            <Table.HeaderCell width={3}>type</Table.HeaderCell>
          </Table.Row>
        </Table.Header>
        <Table.Body>{taskList}</Table.Body>
      </Table>
    </div>
  )
}

export default TasksTable
