package com.tp.dwh.repo;

import com.tp.dwh.model.CXBitCdr;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CXBitCdrRepo extends JpaRepository<CXBitCdr, Long>, CXBitCdrRepoCustom {

}