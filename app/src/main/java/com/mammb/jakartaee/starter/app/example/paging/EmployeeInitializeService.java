package com.mammb.jakartaee.starter.app.example.paging;

import com.mammb.jakartaee.starter.domail.example.paging.Department;
import com.mammb.jakartaee.starter.domail.example.paging.Employee;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Startup
@Singleton
public class EmployeeInitializeService {

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void init() {
        TypedQuery<Long> employeeQuery = em.createQuery(String.format("SELECT COUNT(e) FROM %s e", Employee.NAME), Long.class);
        TypedQuery<Long> departmentQuery = em.createQuery(String.format("SELECT COUNT(e) FROM %s e", Department.NAME), Long.class);
        if (employeeQuery.getSingleResult() == 0 && departmentQuery.getSingleResult() == 0) {

            var marketing = Department.of("Marketing");
            var finance = Department.of("Finance");
            var sales = Department.of("Sales");
            var operations = Department.of("Operations");
            em.persist(marketing);
            em.persist(finance);
            em.persist(sales);
            em.persist(operations);

            em.persist(Employee.of("James Carter").belong(marketing));
            em.persist(Employee.of("Helen Leary").belong(marketing));
            em.persist(Employee.of("Linda Douglas").belong(marketing));
            em.persist(Employee.of("Rafael Ortega").belong(finance));
            em.persist(Employee.of("Henry Stevens").belong(finance));
            em.persist(Employee.of("Sharon Jenkins").belong(finance));
            em.persist(Employee.of("George Franklin").belong(finance));
            em.persist(Employee.of("Betty Davis").belong(sales));
            em.persist(Employee.of("Eduardo Rodriquez").belong(sales));
            em.persist(Employee.of("Harold Davis").belong(sales));
            em.persist(Employee.of("Peter McTavish").belong(sales));
            em.persist(Employee.of("Jean Coleman").belong(sales));
            em.persist(Employee.of("Jeff Black").belong(sales));
            em.persist(Employee.of("Maria Escobito").belong(operations));
            em.persist(Employee.of("David Schroeder").belong(operations));
            em.persist(Employee.of("Carlos Estaban").belong(operations));
            em.persist(Employee.of("Theodore MacDonald").belong(sales));
            em.persist(Employee.of("Casey Wedgewood").belong(sales));
            em.persist(Employee.of("Charles Stewart").belong(sales));
            em.persist(Employee.of("Guinnes Burnett").belong(sales));
            em.persist(Employee.of("Maurice Baines").belong(sales));
            em.persist(Employee.of("Roderick Lonnie Arthurs").belong(sales));
            em.persist(Employee.of("Bruno Adams").belong(sales));
            em.persist(Employee.of("Bullington Griffin Anvil").belong(sales));
            em.persist(Employee.of("Eliot McWilliams").belong(sales));
            em.persist(Employee.of("Mauris Cory Lovegood").belong(sales));
            em.persist(Employee.of("Merlin Haddon").belong(sales));
            em.persist(Employee.of("Kyle Gale").belong(sales));
            em.persist(Employee.of("Elijah Stephen").belong(sales));
            em.persist(Employee.of("Elton Sitwell").belong(sales));
            em.persist(Employee.of("Jack Faulcon").belong(sales));
            em.persist(Employee.of("Jerome Sheppard").belong(sales));
            em.persist(Employee.of("Ham Marini").belong(sales));
            em.persist(Employee.of("Dennis Bergman").belong(sales));
            em.persist(Employee.of("Ward Eugene Henrie").belong(sales));
            em.persist(Employee.of("Gerry Bush").belong(sales));
            em.persist(Employee.of("Lex Blythe").belong(sales));
            em.persist(Employee.of("Nicolas Creagh").belong(sales));
            em.persist(Employee.of("Egon Lattimore").belong(sales));
            em.persist(Employee.of("Roderic Pucey").belong(sales));
            em.persist(Employee.of("Talcott Egon Ruis").belong(sales));
            em.persist(Employee.of("Caspar Carnegie").belong(sales));
            em.persist(Employee.of("Carlos Mahoney").belong(sales));
            em.persist(Employee.of("Donald Calvin").belong(sales));
            em.persist(Employee.of("Immanuel Quirk").belong(sales));
            em.persist(Employee.of("Guinnes Gerald Coen").belong(sales));
            em.persist(Employee.of("Gil-Silvester Firth").belong(sales));
            em.persist(Employee.of("Moses Larkins").belong(sales));
            em.persist(Employee.of("Adolphus Kemble").belong(sales));
            em.persist(Employee.of("Borden O'Toole").belong(sales));
            em.persist(Employee.of("Constatin Watmore").belong(sales));
            em.persist(Employee.of("Hob McClellan").belong(sales));
            em.persist(Employee.of("Peter Rumbelow").belong(sales));
            em.persist(Employee.of("Immanuel Taverner").belong(sales));
            em.persist(Employee.of("Mauricio Coomes").belong(sales));
            em.persist(Employee.of("Gideon Woolf").belong(sales));
            em.persist(Employee.of("Theobald Hornby").belong(sales));
            em.persist(Employee.of("Horatio Hutson").belong(sales));
            em.persist(Employee.of("Kenneth Rodger MacIntosh").belong(sales));
            em.persist(Employee.of("Alfred Danny Wickins").belong(sales));
            em.persist(Employee.of("Casey Effinger").belong(sales));
            em.persist(Employee.of("Aloysius Rodgers").belong(sales));
            em.persist(Employee.of("Anton Emmenual Everest").belong(sales));
            em.persist(Employee.of("Josiah Read").belong(sales));
            em.persist(Employee.of("Gabriel MacKenzie").belong(sales));
            em.persist(Employee.of("Alf Primrose").belong(sales));
            em.persist(Employee.of("Tamara Cummings").belong(operations));
            em.persist(Employee.of("Esmeralda Alford").belong(operations));
            em.persist(Employee.of("Kay Chomsky").belong(operations));
            em.persist(Employee.of("Patrice FitzSimons").belong(operations));
            em.persist(Employee.of("Jody Marguerite Maitland").belong(operations));
            em.persist(Employee.of("Doreen Xavier").belong(operations));
            em.persist(Employee.of("Aurora MacKintosh").belong(operations));
            em.persist(Employee.of("Drew Minter").belong(operations));
            em.persist(Employee.of("Roberta-Selina Calder").belong(operations));
            em.persist(Employee.of("Orla Eve McCorkell").belong(operations));
            em.persist(Employee.of("Noreen Simon").belong(operations));
            em.persist(Employee.of("Mckenzie Heather Hays").belong(operations));
            em.persist(Employee.of("Chloe Steiner").belong(operations));
            em.persist(Employee.of("Eliza Reece").belong(operations));
            em.persist(Employee.of("Lily Gissing").belong(operations));
            em.persist(Employee.of("Agatha Field").belong(operations));
            em.persist(Employee.of("Myla Buckingham").belong(operations));
            em.persist(Employee.of("America Livingston").belong(operations));
            em.persist(Employee.of("Esmeralda Carlisle").belong(operations));
            em.persist(Employee.of("Martina Allingham").belong(operations));
            em.persist(Employee.of("Serenity Hodgkinson").belong(operations));
            em.persist(Employee.of("Wallace Parkin").belong(operations));
            em.persist(Employee.of("Delilah Nash").belong(operations));
            em.persist(Employee.of("Leyla Livermore").belong(operations));
            em.persist(Employee.of("Bonny Maitland").belong(operations));
            em.persist(Employee.of("Stacy O'Leary").belong(operations));
            em.persist(Employee.of("Brunetta Hyde").belong(operations));
            em.persist(Employee.of("Mariah Bromley").belong(operations));
            em.persist(Employee.of("Joanne-Mindy Scanlan").belong(operations));
            em.persist(Employee.of("Emma Jeffrey").belong(operations));
            em.persist(Employee.of("Loraine Oxley").belong(operations));
            em.persist(Employee.of("Tracy Baxter").belong(operations));
            em.persist(Employee.of("Tricia Edison").belong(operations));
            em.persist(Employee.of("Violetta Warren").belong(operations));
            em.persist(Employee.of("Kerry Copperfield").belong(operations));
            em.persist(Employee.of("Margareta Warleigh").belong(operations));
            em.persist(Employee.of("Hester-Yvonne Tierney").belong(operations));
            em.persist(Employee.of("Georgina Geraldine Heasman").belong(operations));
            em.persist(Employee.of("Deirdre Wiat").belong(operations));
            em.persist(Employee.of("Marguerite Vanessa Mogridge").belong(operations));
            em.persist(Employee.of("Patsy Hyde").belong(operations));
            em.persist(Employee.of("Georgiana-Opal Conroy").belong(operations));
            em.persist(Employee.of("Sharlotte Irene Cumberbatch").belong(operations));
            em.persist(Employee.of("Beatrix FitzSimons").belong(operations));
            em.persist(Employee.of("Angela McPherson").belong(operations));
            em.persist(Employee.of("Vanessa Spiers").belong(operations));
            em.persist(Employee.of("Selenia Lawes").belong(operations));
            em.persist(Employee.of("Tara Mansfield").belong(operations));
            em.persist(Employee.of("Sandy Haywood").belong(operations));
            em.persist(Employee.of("Marjorie Heidi Cleland").belong(operations));

        }
    }
}
