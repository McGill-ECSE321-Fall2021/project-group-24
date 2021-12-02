import React from 'react';
import {View, Image} from 'react-native';
import {Button, Card, Title, Paragraph, Text} from 'react-native-paper';

const RoomBookingCard = ({roombooking, buttons}) => {
  return (
    <Card
      style={{
        width: '95%',
        marginHorizontal: '2.5%',
        borderRadius: 15,
        elevation: 2,
        marginVertical: 10,
      }}>
      <Card.Content style={{alignItems: 'center'}}>
        <Title>{roombooking.roomNum}</Title>
        <Paragraph>Date: {roombooking.date}</Paragraph>
        <Paragraph>
          Day: {roombooking.dayOfWeek.substring(0, 1)}
          {roombooking.dayOfWeek.toLowerCase().substring(1)}
        </Paragraph>
        <Paragraph>
          Start time: {roombooking.startTime.substring(0, 5)}
        </Paragraph>
        <Paragraph>End time: {roombooking.endTime.substring(0, 5)}</Paragraph>
      </Card.Content>
      <Card.Actions style={{alignSelf: 'center'}}>{buttons}</Card.Actions>
    </Card>
  );
};

export default RoomBookingCard;
