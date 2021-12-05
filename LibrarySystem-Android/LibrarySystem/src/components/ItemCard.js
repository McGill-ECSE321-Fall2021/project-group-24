import React, {useState} from 'react';
import {View, Image, Text} from 'react-native';
import {
  Card,
  Title,
  Paragraph,
  ActivityIndicator,
  Divider,
} from 'react-native-paper';

//this component takes in an 'item' object as a parameter and a buttons object that contains
//any buttons that we want to render.
const ItemCard = ({item, reservationDetails, buttons}) => {
  const [loading, setLoading] = useState(true);

  return (
    <Card
      style={{
        marginHorizontal: '5%',
        borderRadius: 15,
        elevation: 2,
        marginVertical: 5,
      }}>
      <Card.Content>
        <Title style={{alignSelf: 'center'}}>{item.itemTitle}</Title>
        <View
          style={{
            flexDirection: 'row',
          }}>
          <Image
            style={{
              marginRight: 20,
              flex: 1,
              resizeMode: 'contain',
            }}
            loadingIndicatorSource={<Text>loading</Text>}
            onLoadEnd={() => {
              setLoading(false);
              console.log('loaded');
            }}
            source={{
              uri: item.imageUrl,
            }}
          />
          {loading && (
            <View
              style={{
                width: '32%',
                position: 'absolute',
                alignSelf: 'center',
                justifyContent: 'center',
              }}>
              <Text style={{textAlign: 'center'}}>No image</Text>
            </View>
          )}
          {/*
            Flex allows us to have this View take up 2/{total flex} amount of space.
            This view has a flex of 2, and the image has a flex of 1, so the image will
            take up 1/3 of the space, and the view below with the info will take up the
            rest of the 2/3.
            */}
          <View style={{flex: 2}}>
            <Paragraph>Type: {item.type}</Paragraph>
            {item.author ? <Paragraph>Author: {item.author}</Paragraph> : null}
            {item.artist ? <Paragraph>Artist: {item.artist}</Paragraph> : null}
            {item.publisher ? (
              <Paragraph>Publisher: {item.publisher}</Paragraph>
            ) : null}
            {item.recordingLabel ? (
              <Paragraph>Recording Label: {item.recordingLabel}</Paragraph>
            ) : null}
            {item.productionCompany ? (
              <Paragraph>
                Production Company: {item.productionCompany}
              </Paragraph>
            ) : null}
            {item.movieCase ? (
              <Paragraph>Movie Cast: {item.movieCase}</Paragraph>
            ) : null}
            {item.director ? (
              <Paragraph>Director: {item.director}</Paragraph>
            ) : null}
            {item.issueNumber ? (
              <Paragraph>Issue Number: {item.issueNumber}</Paragraph>
            ) : null}
            {item.producer ? (
              <Paragraph>Producer: {item.producer}</Paragraph>
            ) : null}
            {item.genre ? <Paragraph>Genre: {item.genre}</Paragraph> : null}
            {item.publishDate ? (
              <Paragraph>Publish Date: {item.publishDate}</Paragraph>
            ) : null}
            {item.isReservable && !reservationDetails ? (
              <Paragraph>
                Next Available Date: {item.nextAvailableDate}
              </Paragraph>
            ) : null}
            {item.isReservable && reservationDetails ? (
              <>
                <Paragraph>
                  Reservation Start Date: {reservationDetails.startDate}
                </Paragraph>
                <Paragraph>
                  Reservation: {reservationDetails.itemReservationId}
                </Paragraph>
                <Paragraph>
                  Checked out?: {reservationDetails.isCheckedOut.toString()}
                </Paragraph>
                <Paragraph>
                  Num of renewals left: {reservationDetails.numOfRenewalsLeft}
                </Paragraph>
                <Paragraph>Reserved for: {reservationDetails.idNum}</Paragraph>
              </>
            ) : null}
          </View>
        </View>
      </Card.Content>
      <Card.Actions style={{alignSelf: 'center'}}>{buttons}</Card.Actions>
    </Card>
  );
};

export default ItemCard;
