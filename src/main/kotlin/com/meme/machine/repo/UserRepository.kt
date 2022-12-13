package com.meme.machine.repo

import com.meme.machine.model.UserResponse
import com.meme.machine.model.UserTable
import com.meme.machine.model.dao.UserDao
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement

class UserRepository : UserDao {

    override suspend fun insert(userName: String, userEmail: String, userPassword: String): UserResponse? {
        var statement: InsertStatement<Number>? = null
        dbQuery {
            statement = UserTable.insert { user ->
                user[UserTable.userName] = userName
                user[UserTable.userEmail] = userEmail
                user[UserTable.userPassword] = userPassword
            }
        }
        return statement?.resultedValues?.get(0)?.rowToStudent()
    }

    override suspend fun getAllUsers(): List<UserResponse> =
        dbQuery {
            UserTable.selectAll().mapNotNull { resultRow ->
                resultRow.rowToStudent()
            }
        }


    override suspend fun getUserByID(userId: Int): UserResponse? =
        dbQuery {
            UserTable.select {
                UserTable.userId.eq(userId)
            }.map {
                it.rowToStudent()
            }.singleOrNull()
        }


    override suspend fun deleteById(userId: Int): Int =
        dbQuery {
            UserTable.deleteWhere {
                UserTable.userId.eq(userId)
            }
        }


    override suspend fun updateById(userId: Int, userName: String, userEmail: String, userPassword: String): Int =
        dbQuery {
            UserTable.update({ UserTable.userId.eq(userId) }) { user ->
                user[UserTable.userEmail] = userEmail
                user[UserTable.userName] = userName
                user[UserTable.userPassword] = userPassword
            }
        }
}


fun ResultRow?.rowToStudent(): UserResponse? {
    return if (this == null) {
        null
    } else {
        UserResponse(
            userEmail = this[UserTable.userEmail],
            userId = this[UserTable.userId],
            userName = this[UserTable.userName]
        )
    }

}