package com.bbs.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbs.entites.Details;
import com.bbs.repos.DetailsRepository;
import com.bbs.utilities.ImageUtilities;

@Service
public class DetailsServiceImpl implements DetailsService {

	@Autowired
	private DetailsRepository repo;
	

	@Override
	public Optional<Details> findOptionalByUsername(String username) {
		return repo.findOptionalByUsername(username);
	}

	@Override
	public Details save(Details details) {
		
		if (details.getPhoto() == null) {
			try {
				BufferedImage image = ImageUtilities.getImageFromFile("none.jpg",true);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(image, "jpg", baos);
				byte[] bytes = baos.toByteArray();
				details.setPhoto(bytes); // local file in project
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return repo.save(details);
	}

	@Override
	public List<Details> findAll() {
		return repo.findAll();
	}

}
