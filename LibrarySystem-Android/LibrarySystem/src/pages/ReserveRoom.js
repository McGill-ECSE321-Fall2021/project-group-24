import React, {useState} from 'react';
import {View} from 'react-native';
import axios from 'axios';
import {ScrollView} from 'react-native-gesture-handler';
import RoomCard from '../components/RoomCard';
import {
  DefaultTheme,
  TextInput,
  Title,
  Button,
  FAB,
  Dialog,
  Portal,
  Paragraph,
} from 'react-native-paper';
import DatePicker from 'react-native-date-picker';
import moment from 'moment';
const baseUrl = 'https://librarysystem-backend-321.herokuapp.com/';
//this is from vue js file
var AXIOS = axios.create({
  baseURL: baseUrl,
});

const ReserveRoom = ({route, navigation}) => {
  const [idNum, setIdNum] = useState(
    DefaultTheme.currentUser.isPatron ? DefaultTheme.currentUser.idNum : '',
  );
  const [date, setDate] = useState(new Date());
  const [datePickerOpen, setDatePickerOpen] = useState(false);
  const [startTime, setStartTime] = useState(new Date());
  const [startTimePickerOpen, setStartTimePickerOpen] = useState(false);
  const [endTime, setEndTime] = useState(new Date());
  const [endTimePickerOpen, setEndTimePickerOpen] = useState(false);

  const [error, setError] = useState('');
  const [response, setResponse] = useState('');

  const submitReservation = () => {
    AXIOS.post(
      'api/roombookings/add_roombooking/?currentUserId=' +
        DefaultTheme.currentUser.idNum +
        '&idNum=' +
        idNum +
        '&roomNum=' +
        route.params.room.roomNum +
        '&date=' +
        moment(date).format('YYYY-MM-DD') +
        '&startTime=' +
        (startTime.getHours() < 10
          ? '0' + startTime.getHours()
          : startTime.getHours()) +
        ':' +
        (startTime.getMinutes() == 0 ? '00' : startTime.getMinutes()) +
        '&endTime=' +
        (endTime.getHours() < 10
          ? '0' + endTime.getHours()
          : endTime.getHours()) +
        ':' +
        (endTime.getMinutes() == 0 ? '00' : endTime.getMinutes()),
    )
      .then(() => {
        setResponse('Room Booking Created');
        setError('');
        navigation.navigate('ViewAllRoomBookings');
      })
      .catch(e => {
        setResponse('');
        if (e.response.data.error) {
          setError(e.response.data.error);
        } else {
          setError(e.response.data);
        }
      });
  };
  return (
    <>
      <ScrollView>
        <Title style={{textAlign: 'center', marginTop: '20%'}}>
          Reserve room:
        </Title>
        <View style={{alignItems: 'center'}}>
          <RoomCard room={route.params.room} />
        </View>

        <View style={{marginHorizontal: '20%', marginTop: '10%'}}>
          <TextInput
            label="Patron's id number"
            mode="outlined"
            disabled={DefaultTheme.currentUser.isPatron}
            value={idNum}
            onChangeText={setIdNum}
          />
          <Button
            mode="contained"
            onPress={() => setDatePickerOpen(true)}
            style={{marginTop: '10%'}}>
            Date: {date.toDateString()}
          </Button>
          <Button
            mode="contained"
            onPress={() => setStartTimePickerOpen(true)}
            style={{marginTop: '10%'}}>
            Start Time: {startTime.toLocaleTimeString()}
          </Button>
          <Button
            mode="contained"
            onPress={() => setEndTimePickerOpen(true)}
            style={{marginTop: '10%'}}>
            End Time: {endTime.toLocaleTimeString()}
          </Button>
        </View>

        <DatePicker
          modal
          mode="date"
          open={datePickerOpen}
          date={date}
          onConfirm={date => {
            setDatePickerOpen(false);
            setDate(date);
          }}
          onCancel={() => {
            setDatePickerOpen(false);
          }}
        />
        <DatePicker
          modal
          mode="time"
          open={startTimePickerOpen}
          date={startTime}
          minuteInterval={15}
          onConfirm={startTime => {
            setStartTimePickerOpen(false);
            setStartTime(startTime);
          }}
          onCancel={() => {
            setStartTimePickerOpen(false);
          }}
        />
        <DatePicker
          modal
          minimumDate={startTime}
          mode="time"
          open={endTimePickerOpen}
          date={endTime}
          minuteInterval={15}
          onConfirm={endTime => {
            setEndTimePickerOpen(false);
            setEndTime(endTime);
          }}
          onCancel={() => {
            setEndTimePickerOpen(false);
          }}
        />
        <Portal>
          <Dialog visible={error || response}>
            <Dialog.Title>{error ? 'Error' : 'Response'}</Dialog.Title>
            <Dialog.Content>
              <Paragraph>{error ? error : response}</Paragraph>
            </Dialog.Content>
            <Dialog.Actions>
              <Button
                onPress={() => {
                  if (error) {
                    setError('');
                    setResponse('');
                  } else {
                    setError('');
                    setResponse('');
                  }
                }}>
                Confirm
              </Button>
            </Dialog.Actions>
          </Dialog>
        </Portal>
      </ScrollView>
      <FAB
        onPress={() => {
          submitReservation();
        }}
        style={{
          zIndex: 1,
          position: 'absolute',
          bottom: 20,
          right: 50,
          alignSelf: 'center',
        }}
        label="Submit"
      />
    </>
  );
};

export default ReserveRoom;
