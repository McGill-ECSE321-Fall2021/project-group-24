package ca.mcgill.ecse321.librarysystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.librarysystem.model.*;
import java.sql.Time;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestLibraryHourPersistence {

  @Autowired
  private LibraryHourRepository libraryHourRepository;

  @BeforeEach
  public void clearDatabase() {
    // First, we clear registrations to avoid exceptions due to inconsistencies
    //	      librarySystemRepository.deleteAll();
    // Then we can clear the other tables

    libraryHourRepository.deleteAll();
  }

  @Test
  public void testPersistAndLoadLibraryHour() {
    LibraryHour hour = new LibraryHour();

    Time libraryHourTime = Time.valueOf("00:00:00");
    String timeSlotId = "hour id";
    hour.setTimeSlotId(timeSlotId);
    hour.setDayOfWeek(TimeSlot.DayOfWeek.MONDAY);
    hour.setStartTime(libraryHourTime);
    hour.setEndTime(libraryHourTime);

    libraryHourRepository.save(hour);

    hour = null;

    hour = libraryHourRepository.findHourByTimeSlotId(timeSlotId);

    assertNotNull(hour);

    assertEquals(timeSlotId, hour.getTimeSlotId());
    assertEquals(TimeSlot.DayOfWeek.MONDAY, hour.getDayOfWeek());
    assertEquals(libraryHourTime, hour.getStartTime());
    assertEquals(libraryHourTime, hour.getEndTime());
  }
}
