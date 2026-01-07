package sk.ukf.latriesta.service.pizza;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.ukf.latriesta.entity.Tag;
import sk.ukf.latriesta.repository.TagRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public Optional<Tag> findById(Integer id) {
        return tagRepository.findById(id);
    }

    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    public void deleteById(Integer id) {
        tagRepository.deleteById(id);
    }
}