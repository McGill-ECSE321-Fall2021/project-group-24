import React from 'react';
import {Flatlist, View, Image} from 'react-native';
import {Button, Card, Title, Paragraph} from 'react-native-paper';

const RoomCard = ({room, buttons}) => {
  return (
    <Card
      style={{
        width: '45%',
        marginHorizontal: '2.5%',
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
