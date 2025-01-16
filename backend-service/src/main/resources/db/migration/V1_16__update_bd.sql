-- Для внешнего ключа на address в таблице user
ALTER TABLE musical_school.user
    DROP CONSTRAINT fk_user_on_address;

ALTER TABLE musical_school.user
    ADD CONSTRAINT fk_user_on_address
        FOREIGN KEY (address) REFERENCES musical_school.adress(id)
            ON DELETE CASCADE;

-- Для внешнего ключа на idCard в таблице user
ALTER TABLE musical_school.user
    DROP CONSTRAINT fk_user_on_idCard;

ALTER TABLE musical_school.user
    ADD CONSTRAINT fk_user_on_idCard
        FOREIGN KEY (id_card) REFERENCES musical_school.idcard(id)
            ON DELETE CASCADE;
