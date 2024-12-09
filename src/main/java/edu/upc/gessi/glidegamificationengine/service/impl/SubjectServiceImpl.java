package edu.upc.gessi.glidegamificationengine.service.impl;

import edu.upc.gessi.glidegamificationengine.dto.SubjectDTO;
import edu.upc.gessi.glidegamificationengine.entity.SubjectEntity;
import edu.upc.gessi.glidegamificationengine.mapper.SubjectMapper;
import edu.upc.gessi.glidegamificationengine.repository.SubjectRepository;
import edu.upc.gessi.glidegamificationengine.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public List<SubjectDTO> getSubjects(){
        List<SubjectEntity> subjectEntities = subjectRepository.findAll();
        return subjectEntities.stream().map((SubjectMapper::mapToSubjectDto))
                .collect(Collectors.toList());
    }
}
