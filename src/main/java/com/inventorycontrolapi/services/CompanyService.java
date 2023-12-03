package com.inventorycontrolapi.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.inventorycontrolapi.domains.CompanyDomain;
import com.inventorycontrolapi.domains.exceptions.InvalidCompanyDomainException;
import com.inventorycontrolapi.dtos.company.SignInCompanyDTORequest;
import com.inventorycontrolapi.dtos.company.SignInCompanyDTOResponse;
import com.inventorycontrolapi.dtos.company.SignUpCompanyDTORequest;
import com.inventorycontrolapi.dtos.company.SignUpCompanyDTOResponse;
import com.inventorycontrolapi.models.CompanyModel;
import com.inventorycontrolapi.repositories.CompanyRepository;
import com.inventorycontrolapi.services.exceptions.EmailAlreadyRegisteredException;
import com.inventorycontrolapi.services.exceptions.EmailOrPasswordInvalidException;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Service
public class CompanyService {
	private CompanyRepository companyRepository;
	private JwtService jwtService;

	public CompanyService(CompanyRepository companyRepository, JwtService jwtService) {
		this.companyRepository = companyRepository;
		this.jwtService = jwtService;
	}

	public SignUpCompanyDTOResponse signUp(SignUpCompanyDTORequest signUpCompanyDTORequest) throws InvalidCompanyDomainException, EmailAlreadyRegisteredException {
		CompanyDomain.validate(
			signUpCompanyDTORequest.getName(),
			signUpCompanyDTORequest.getEmail(),
			signUpCompanyDTORequest.getPassword()
		);

		Optional<CompanyModel> findCompanyModelByEmail = this.companyRepository.findByEmail(
			signUpCompanyDTORequest.getEmail()
		);
		
		if (!findCompanyModelByEmail.isEmpty()) {
			throw new EmailAlreadyRegisteredException();
		}

		String hashPassword = BCrypt.withDefaults().hashToString(12, signUpCompanyDTORequest.getPassword().toCharArray());

		CompanyModel companyModel = new CompanyModel(
			signUpCompanyDTORequest.getName(),
			signUpCompanyDTORequest.getEmail(),
			hashPassword
		);

		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);

		return new SignUpCompanyDTOResponse(saveCompanyModel.getCompanyId().toString());
	}

	public SignInCompanyDTOResponse signIn(SignInCompanyDTORequest signInCompanyDTORequest) throws EmailOrPasswordInvalidException {
		Optional<CompanyModel> findCompanyModelByEmail = this.companyRepository.findByEmail(
			signInCompanyDTORequest.getEmail()
		);

		if (findCompanyModelByEmail.isEmpty()) {
			throw new EmailOrPasswordInvalidException();
		}

		boolean validPassword = BCrypt.verifyer().verify(
            signInCompanyDTORequest.getPassword().toCharArray(),
            findCompanyModelByEmail.get().getPassword()
        ).verified;

        if (!validPassword) {
            throw new EmailOrPasswordInvalidException();
        }

        String jwt = this.jwtService.generateJwt(findCompanyModelByEmail.get().getCompanyId().toString());

        return new SignInCompanyDTOResponse(jwt);
	}
}
