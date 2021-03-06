package ca.mcgill.ecse321.librarysystem.dao;

import ca.mcgill.ecse321.librarysystem.model.*;
import java.sql.Time;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ShiftRepository extends CrudRepository<Shift, String> {
  List<Shift> findShiftByLibrarianId(String librarianId);
  Shift findShiftByTimeSlotId(String timeSlotId);
  Shift findShiftByLibrarianIdAndDayOfWeek(
    String librarianId,
    TimeSlot.DayOfWeek dayOfWeek
  );
  Shift findShiftByLibrarianIdAndDayOfWeekAndStartTime(
    String librarianId,
    TimeSlot.DayOfWeek dayOfWeek,
    Time StartTime
  );
}
