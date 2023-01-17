CREATE TABLE university.student_nationality(
    student_id INT NOT NULL,
    nationality_id INT NOT NULL,
    PRIMARY KEY (student_id, nationality_id),
    FOREIGN KEY (student_id) REFERENCES student(id),
    FOREIGN KEY (nationality_id) REFERENCES nationality(id)
)