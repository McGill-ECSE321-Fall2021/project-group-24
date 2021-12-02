import React from 'react';
import {View, Image} from 'react-native';
import {Button, Card, Title, Paragraph} from 'react-native-paper';

const RoomCard = ({room, buttons}) => {
  return (
    <Card
      style={{
        margin: '2.5%',
        width: '45%',
        borderRadius: 15,
        elevation: 2,
        marginVertical: 5,
      }}>
      <Card.Content style={{alignItems: 'center'}}>
        <Title>{room.roomNum}</Title>
        <Paragraph>Capacity: {room.capacity}</Paragraph>
      </Card.Content>
      <Card.Actions style={{alignSelf: 'center'}}>{buttons}</Card.Actions>
    </Card>
  );
};

export default RoomCard;
