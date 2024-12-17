package com.example.capston3.Service;

import com.example.capston3.ApiResponse.ApiException;
import com.example.capston3.DTO.BookingCourseOutDTO;
import com.example.capston3.Model.BookingCourse;
import com.example.capston3.Model.Course;
import com.example.capston3.Model.User;
import com.example.capston3.Repository.BookingCourseRepository;
import com.example.capston3.Repository.CourseRepository;
import com.example.capston3.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingCourseService {
    private final BookingCourseRepository bookingCourseRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public List<BookingCourseOutDTO> getAllBookingCourses() {
        List<BookingCourse> bookingCourses = bookingCourseRepository.findAll();
        List<BookingCourseOutDTO> bookingCourseOutDTOs = new ArrayList<>();
        for (BookingCourse bookingCourse : bookingCourses) {
            bookingCourseOutDTOs.add(new BookingCourseOutDTO(bookingCourse.getBookingDate(),bookingCourse.getCourseStartDate(),bookingCourse.getCourseEndDate()));
        }
        return bookingCourseOutDTOs;
    }

    public void addBookingCourse(Integer user_id,Integer course_id, BookingCourse bookingCourse) {
        User user = userRepository.findUserById(user_id);
        Course course = courseRepository.findCourseById(course_id);
        bookingCourse.setUser(user);
        bookingCourse.setCourse(course);
        bookingCourseRepository.save(bookingCourse);
    }

    public void updateBookingCourse(Integer bookingCourse_id, BookingCourse bookingCourse) {
        BookingCourse bookingCourse1 = bookingCourseRepository.findBookingCourseById(bookingCourse_id);
        if (bookingCourse1 == null) {
            throw new ApiException("Booking course not found");
        }
        bookingCourse1.setCourseStartDate(bookingCourse.getCourseStartDate());
        bookingCourse1.setCourseEndDate(bookingCourse.getCourseEndDate());
        bookingCourseRepository.save(bookingCourse1);
    }

    public void deleteBookingCourse(Integer bookingCourse_id) {
        BookingCourse bookingCourse1 = bookingCourseRepository.findBookingCourseById(bookingCourse_id);
        if (bookingCourse1 == null) {
            throw new ApiException("Booking course not found");
        }
        bookingCourseRepository.delete(bookingCourse1);
    }
}