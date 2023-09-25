package com.patri.plataforma.restapi.repository;

import com.patri.plataforma.restapi.model.Report;
import com.patri.plataforma.restapi.repository.custom.ItemRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import com.patri.plataforma.restapi.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom
{
    public List<Item> findByReport(Report report);
}
