package com.arquimentor.platform.arquimentor.application.internal.commandServices;


import com.arquimentor.platform.arquimentor.domain.model.aggregates.Student;
import com.arquimentor.platform.arquimentor.domain.model.aggregates.StudentProfile;
import com.arquimentor.platform.arquimentor.domain.model.commands.CreateStudentProfileCommand;
import com.arquimentor.platform.arquimentor.domain.model.commands.UpdateStudentProfileCommand;
import com.arquimentor.platform.arquimentor.domain.model.valueobjects.PhoneNumber;
import com.arquimentor.platform.arquimentor.domain.model.valueobjects.UserProfilePhoto;
import com.arquimentor.platform.arquimentor.domain.services.StudentProfileCommandService;
import com.arquimentor.platform.arquimentor.infrastructure.persistence.jpa.repositories.StudentProfileRepository;
import com.arquimentor.platform.arquimentor.infrastructure.persistence.jpa.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentProfileCommandServiceImpl implements StudentProfileCommandService {
    private final StudentProfileRepository studentProfileRepository;
    private final StudentRepository studentRepository;

    public StudentProfileCommandServiceImpl(StudentProfileRepository studentProfileRepository, StudentRepository studentRepository) {
        this.studentProfileRepository = studentProfileRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Optional<StudentProfile> handle(Long id) {
        return studentProfileRepository.findById(id);
    }

    @Override
    public List<StudentProfile> findAll() {
        return studentProfileRepository.findAll();
    }

    @Override
    public List<StudentProfile> findStudentProfileByIdStudent(Long idStudent) {

        return studentProfileRepository.findByStudentId(idStudent);
    }

    @Override
    public Long handle(CreateStudentProfileCommand command) {
        Optional<Student> student = studentRepository.findById(command.studentId());

        if (student.isPresent()) {
            StudentProfile studentProfile = new StudentProfile(
                    new PhoneNumber(command.phonenumber()),
                    command.description(),
                    new UserProfilePhoto(command.userprofilephoto()),
                    student.get()
            );

            StudentProfile savedProfile = studentProfileRepository.save(studentProfile);
            return savedProfile.getId();
        } else {
            return null;
        }
    }
    @Override
    public Optional<StudentProfile> updateStudentProfileByID(UpdateStudentProfileCommand command) {
        Optional<StudentProfile> existingProfile = studentProfileRepository.findById(command.id());

        if (existingProfile.isPresent()) {
            StudentProfile updatedProfile = existingProfile.get();

            if (command.phonenumber() != null) {
                updatedProfile.updatePhoneNumber(command.phonenumber());
            }

            if (command.description() != null) {
                updatedProfile.updateDescription(command.description());
            }

            if (command.userprofilephoto() != null) {
                updatedProfile.updateUserProfilePhoto(new UserProfilePhoto(command.userprofilephoto().imageUrl()));
            }

            StudentProfile savedProfile = studentProfileRepository.save(updatedProfile);
            return Optional.of(savedProfile);
        } else {
            // Maneja el caso en el que no se encuentra el perfil a actualizar
            return Optional.empty();
        }
    }
}