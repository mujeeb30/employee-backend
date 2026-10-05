DROP PROCEDURE IF EXISTS getEmployeesBySalaryOrder;

CREATE PROCEDURE getEmployeesBySalaryOrder(IN sortDirection VARCHAR(4))
BEGIN
    IF UPPER(sortDirection) NOT IN ('ASC', 'DESC') THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'sortDirection must be ASC or DESC';
    END IF;

    SELECT *
    FROM employees
    ORDER BY
        CASE WHEN UPPER(sortDirection) = 'ASC' THEN salary END ASC,
        CASE WHEN UPPER(sortDirection) = 'DESC' THEN salary END DESC,
        id ASC;
END;
