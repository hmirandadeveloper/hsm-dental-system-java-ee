package persistencia.generics;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

public abstract class GenericDAO<T> {
	private final static String UNIT_NAME = "sodontosystemPU";
	
	@PersistenceContext(unitName = UNIT_NAME)
	private EntityManager em;
	
	private Class<T> classeEntidade;
	
	public GenericDAO(Class<T> classeEntidade)
	{
		this.classeEntidade = classeEntidade;
		System.out.println("[SODONTO SYSTEM](DAO) Entidade: " + this.classeEntidade.getSimpleName());
	}
	
	public void salvar(T entidade)
	{
		System.out.println("[SODONTO SYSTEM](DAO) Iniciando persistência...");
		this.em.persist(entidade);
		System.out.println("[SODONTO SYSTEM](DAO) Persistência concluída!");
	}
	
	protected void remover(Object id, Class<T> classe)
	{
		T entidadeASerRemovida = this.em.getReference(classe, id);
		System.out.println("[SODONTO SYSTEM](DAO) Iniciando remoção...");
		this.em.remove(entidadeASerRemovida);
		System.out.println("[SODONTO SYSTEM](DAO) Remoção concluida!");
	}
	
	public void salvarComID(T entidade)
	{
		System.out.println("[SODONTO SYSTEM](DAO) Salvando com ID...");
		this.em.merge(entidade);
	}
	
	public T atualizar(T entidade)
	{
		System.out.println("[SODONTO SYSTEM](DAO) Iniciando atualização...");
		return this.em.merge(entidade);
	}
	
	public T buscar(Object idEntidade)
	{
		return this.em.find(classeEntidade, idEntidade);
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<T> buscarTodos()
	{
		CriteriaQuery cq = this.em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(classeEntidade));
		return em.createQuery(cq).getResultList();		
	}
	
	@SuppressWarnings("unchecked")
	protected T buscarUmResultado(String namedQuery, Map<String, Object> parametros)
	{
		System.out.println("[SODONTO SYSTEM](DAO) Busca por um resultado!");
		
		T resultado = null;
		
		try
		{
			Query query = em.createNamedQuery(namedQuery);
			if(parametros != null && !parametros.isEmpty())
			{
				popularParamentrosDaQuery(query, parametros);
			}
			
			resultado = (T) query.getSingleResult();
			
		}catch(NoResultException e)
		{
			System.out.println("[SODONTO SYSTEM](DAO) Nenhum resultado encontrado!");
			resultado = null;
			
		}catch(Exception e)
		{
			System.out.println("[SODONTO SYSTEM](DAO) Erro na execução da Query: "+ e.getMessage());
		}
		
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> buscarResultadosFiltrados(String namedQuery, Map<String, Object> parametros, int limit, int first)
	{
		System.out.println("[SODONTO SYSTEM](DAO) Busca vários resultados!");
		List<T> resultados = null;
		
		try
		{
			Query query = em.createNamedQuery(namedQuery);
			
			if(first > 0)
			{
				query.setFirstResult(first);
			}
			
			query.setMaxResults(limit);
			
			if(parametros != null & !parametros.isEmpty())
			{
				popularParamentrosDaQuery(query, parametros);
			}
			
			resultados = (List<T>) query.getResultList();

		}catch(NoResultException e)
		{
			System.out.println("[SODONTO SYSTEM](DAO) Nenhum resultado encontrado!");
			
		}catch(Exception e)
		{
			System.out.println("[SODONTO SYSTEM](DAO) Erro na execução da Query: "+ e.getMessage());
		}
		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> buscarResultadosFiltrados(String namedQuery, Map<String, Object> parametros, int limite)
	{
		System.out.println("[SODONTO SYSTEM](DAO) Busca vários resultados!");
		List<T> resultados = null;
		
		try
		{
			Query query = em.createNamedQuery(namedQuery);
			
			query.setMaxResults(limite);
			
			if(parametros != null & !parametros.isEmpty())
			{
				popularParamentrosDaQuery(query, parametros);
			}
			
			resultados = (List<T>) query.getResultList();

		}catch(NoResultException e)
		{
			System.out.println("[SODONTO SYSTEM](DAO) Nenhum resultado encontrado!");
			
		}catch(Exception e)
		{
			System.out.println("[SODONTO SYSTEM](DAO) Erro na execução da Query: "+ e.getMessage());
		}
		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> buscarResultadosFiltrados(String namedQuery, Map<String, Object> parametros)
	{
		System.out.println("[SODONTO SYSTEM](DAO) Busca vários resultados!");
		List<T> resultados = null;
		
		try
		{
			Query query = em.createNamedQuery(namedQuery);
			
			query.setMaxResults(500);
			
			if(parametros != null & !parametros.isEmpty())
			{
				popularParamentrosDaQuery(query, parametros);
			}
			
			resultados = (List<T>) query.getResultList();

		}catch(NoResultException e)
		{
			System.out.println("[SODONTO SYSTEM](DAO) Nenhum resultado encontrado!");
			
		}catch(Exception e)
		{
			System.out.println("[SODONTO SYSTEM](DAO) Erro na execução da Query: "+ e.getMessage());
		}
		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	protected List<String> buscarResultadosTexto(String namedQuery, Map<String, Object> parametros)
	{
		System.out.println("[SODONTO SYSTEM](DAO) Busca vários resultados de Textos!");
		List<String> resultados = null;
		
		try
		{
			Query query = em.createNamedQuery(namedQuery);
			
			if(parametros != null && !parametros.isEmpty())
			{
				popularParamentrosDaQuery(query, parametros);
			}
			
			resultados = (List<String>) query.getResultList();

		}catch(NoResultException e)
		{
			System.out.println("[SODONTO SYSTEM](DAO) Nenhum resultado encontrado!");
			
		}catch(Exception e)
		{
			System.out.println("[SODONTO SYSTEM](DAO) Erro na execução da Query: "+ e.getMessage());
		}
		return resultados;
	}
	
	protected Integer buscarContagemResultado(String namedQuery, Map<String, Object> parametros)
	{
		System.out.println("[SODONTO SYSTEM](DAO) Busca vários resultados como Inteiro!");
		Integer resultado = 0;
		
		try
		{
			Query query = em.createNamedQuery(namedQuery);
			
			if(parametros != null && !parametros.isEmpty())
			{
				popularParamentrosDaQuery(query, parametros);
			}
			
			resultado = ( query.getSingleResult() != null ? ((Number) query.getSingleResult()).intValue() : 0);

		}catch(NoResultException e)
		{
			System.out.println("[SODONTO SYSTEM](DAO) Nenhum resultado encontrado!");
			
		}catch(Exception e)
		{
			System.out.println("[SODONTO SYSTEM](DAO) Erro na execução da Query: "+ e.getMessage());
		}
		return resultado;
	}	
	
	private void popularParamentrosDaQuery(Query query, 
			Map<String, Object> parametros)
	{
		System.out.println("[SODONTO SYSTEM](DAO) Carregando parâmetros...");
		for(Entry<String, Object> entry : parametros.entrySet())
		{
			if(entry.getValue() instanceof String)
			{
				String valor = (String) entry.getValue();
				if(valor.contains("%"))
				{
					valor.substring(1, valor.length() - 1);
					query.setParameter(entry.getKey(), "%" + valor + "%");
				}
				query.setParameter(entry.getKey(), valor);
			}
			else
			{
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
	}

	public EntityManager getEm() {
		return em;
	}
}
