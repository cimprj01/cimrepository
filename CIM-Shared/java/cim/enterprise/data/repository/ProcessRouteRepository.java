package cim.enterprise.data.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import cim.enterprise.data.ProcessRoute;

public interface ProcessRouteRepository extends Repository<cim.enterprise.data.ProcessRoute, String>{

	public List<ProcessRoute> findByProcessId(String processId);
}
