import React from 'react';
import {View, Image} from 'react-native';
import {Button, Card, Title, Paragraph, Text} from 'react-native-paper';

const ItemReservationCard = ({item, buttons}) => {
  return (
    <Card
      style={{
        alignSelf: 'center',
        width: '90%',
        borderRadius: 15,
        elevation: 2,
        marginVertical: 10,
      }}>
      <Card.Content style={{alignItems: 'center'}}>
        <Title>{item.itemTitle}</Title>
        <Title>HELLO</Title>
      </Card.Content>
      <Card.Actions style={{alignSelf: 'center'}}>{buttons}</Card.Actions>
    </Card>
  );
};

export default ItemReservationCard;
