package com.bushelpowered.jaleta.pokedex.repository

import com.bushelpowered.jaleta.pokedex.model.Captured
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CapturedRepository: JpaRepository<Captured, Int> {


}