package kr.codesqaud.cafe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.dto.QuestionDetailsDTO;
import kr.codesqaud.cafe.dto.QuestionWriteDTO;
import kr.codesqaud.cafe.repository.QuestionRepository;

@Service
public class QuestionService {

	private final QuestionRepository repository;

	@Autowired
	public QuestionService(QuestionRepository repository) {
		this.repository = repository;
	}

	public void addQuestion(QuestionWriteDTO dto) {
		repository.save(dto);
	}

	public List<QuestionDetailsDTO> findAllQuestions() {
		return repository.findAll();
	}

}
