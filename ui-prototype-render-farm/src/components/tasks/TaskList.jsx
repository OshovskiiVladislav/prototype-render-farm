import React from 'react'
import { Card, Header, Segment } from 'semantic-ui-react'
import TaskCard from './TaskCard'

function TaskList({ tasks }) {
  const taskList = tasks.map((task) => (
    <TaskCard key={task.id} task={task} link={true} />
  ))

  return tasks.length > 0 ? (
    <Card.Group doubling centered>
      {taskList}
    </Card.Group>
  ) : (
    <Segment padded color="blue">
      <Header textAlign="center" as="h4">
        No tasks ヽ(°□° )/
      </Header>
    </Segment>
  )
}

export default TaskList
