package com.sanatoriodelsalvador.seguridad.commons.hibernate;

import java.io.Serializable;
import java.sql.*;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class UsuarioIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

        Connection connection = session.connection();

        try {
            Statement statement=connection.createStatement();

            ResultSet rs=statement.executeQuery("SELECT MAX(id_usuario) FROM seguridad.seg_usuarios");

            if(rs.next())
            {
                return rs.getInt(1)+1;                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}