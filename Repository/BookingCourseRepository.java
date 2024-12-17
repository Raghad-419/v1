package com.example.capston3.Repository;

import com.example.capston3.Model.BookingCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookingCourseRepository extends JpaRepository<BookingCourse,Integer> {
    BookingCourse findBookingCourseById(Integer id);
}
