package sk.ukf.latriesta.service.pizza;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.ukf.latriesta.entity.Size;
import sk.ukf.latriesta.repository.SizeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SizeService {

    private final SizeRepository sizeRepository;

    @Autowired
    public SizeService(SizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    public List<Size> findAll() {
        return sizeRepository.findByDeletedFalse();
    }

    public Optional<Size> findById(Integer id) {
        return sizeRepository.findByIdAndDeletedFalse(id);
    }

    public Size save(Size size) {
        return sizeRepository.save(size);
    }

    // soft-delete
    public void deleteById(Integer id) {
        Size size = sizeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Veľkosť nenájdená"));
        size.setDeleted(true);
        sizeRepository.save(size);
    }
}