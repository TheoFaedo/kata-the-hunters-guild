# ⚔️ Spring Boot Kata — *The Hunters' Guild*

You are developing the backend of **Monster Guild**, an application used by a guild to manage monster hunting contracts.

The idea is simple: hunters join the guild, contracts become available, and hunters can accept and complete those contracts.

The goal is not to cover every Spring concept, but to naturally practice **Spring Boot, REST, validation, error handling, JPA, and transactions**.

The main version should take roughly **2 to 4 hours**.

---

## 🎯 Rules of the Game

You only need to develop a **Spring Boot REST API**.

You are free to choose:

- your architecture;
- your DTOs;
- your entities;
- your repositories;
- your services;
- the way you represent states.

A few constraints only:

- Java 17+;
- Spring Boot;
- Maven or Gradle;
- Spring Web;
- Spring Data JPA;
- Bean Validation;
- H2 for the database;
- tests are strongly recommended.

And most importantly: **no frontend**.

---

# 🟢 User Story 1 — Join the Guild

> As a future hunter,  
> I want to join the guild,  
> so that I can accept contracts.

A hunter has:

- an identifier;
- a name;
- a level;
- an amount of gold.

When a hunter joins the guild:

- the name is required;
- the name must contain between 3 and 30 characters;
- the hunter starts at **level 1**;
- the hunter starts with **100 gold coins**.

### Acceptance Criteria

```
POST /hunters
```

```
{
  "name": "Geralt"
}
```

should return something similar to:

```
{
  "id": 1,
  "name": "Geralt",
  "level": 1,
  "gold": 100
}
```

An invalid name should produce an appropriate HTTP response containing a clear error message.

### 🧠 What This User Story Makes You Practice

Without telling you exactly how to implement it:

`Controller → validation → domain model → persistence`

---

# 🟢 User Story 2 — The Contract Board

> As a hunter,  
> I want to browse available contracts,  
> so that I can choose my next hunt.

A contract contains:

- a title;
- the targeted monster;
- a minimum required level;
- a gold reward;
- a status.

Possible statuses are:

```
AVAILABLE
ACCEPTED
COMPLETED
```

The application should contain a few contracts at startup.

For example:


| Contract     | Monster   | Level | Reward |
| ------------ | --------- | ----- | ------ |
| Rat Problem  | Giant Rat | 1     | 25     |
| Troll Bridge | Troll     | 3     | 100    |
| Dragon Hunt  | Dragon    | 10    | 1000   |


### Acceptance Criterion

```
GET /contracts?status=AVAILABLE
```

should return only available contracts.

### Small Extra Challenge

Also support:

```
GET /contracts?minReward=100
```

You decide how to handle combinations of filters.

---

# 🟡 User Story 3 — Accept a Contract

> As a hunter,  
> I want to accept a contract,  
> so that I can go hunt the monster.

Suggested endpoint:

```
POST /contracts/{contractId}/accept
```

Body:

```
{
  "hunterId": 42
}
```

A hunter can accept a contract only if:

- the contract exists;
- the hunter exists;
- the contract is `AVAILABLE`;
- the hunter's level is high enough.

Otherwise, the API should return an appropriate error.

For example:

```
{
  "status": 409,
  "message": "Contract is already accepted"
}
```

or:

```
{
  "status": 403,
  "message": "Hunter level is too low"
}
```

You may choose whichever HTTP status codes you consider the most appropriate.

### ⚠️ Interesting Case

Two hunters try to accept the same contract.

The contract must obviously not end up assigned to both of them.

There is no need to build a complex distributed system: simply think about what **the database and transactions** can guarantee.

---

# 🟠 User Story 4 — Complete a Hunt

> As a hunter,  
> I want to report that a contract has been completed,  
> so that I can receive my reward.

```
POST /contracts/{contractId}/complete
```

When the contract is completed:

1. its status becomes `COMPLETED`;
2. the reward is added to the hunter's gold.

Example:

Before:

```
Geralt
Gold: 100
```

Contract:

```
Giant Rat
Reward: 25
```

After:

```
Geralt
Gold: 125
```

The contract can only be completed by **the hunter who accepted it**.

And most importantly:

> Completing the same contract twice must not grant the reward twice.

This is probably the most interesting User Story in the kata.

---

# 🔴 User Story 5 — Hunter Reputation

> As a guild master,  
> I want to know a hunter's performance,  
> so that I can see which members are the most experienced.

```
GET /hunters/{id}/stats
```

Example:

```
{
  "name": "Geralt",
  "level": 3,
  "gold": 475,
  "completedContracts": 7,
  "totalGoldEarned": 375
}
```

But there is one small constraint:

**do not necessarily store all these statistics.**

Some of them may be calculated from existing data.

It is up to you to decide which ones.

---

# 👹 Final Boss — Level Up

One last business rule is added.

Every **3 completed contracts**, a hunter gains one level.

So:

```
0-2 contracts  → level 1
3-5 contracts  → level 2
6-8 contracts  → level 3
...
```

Update your application so that it respects this rule.

But now ask yourself whether `level` should really be a freely modifiable piece of data, or whether it could instead be **derived from business rules**.

There is intentionally no single correct answer.

---

## 🧪 Tests I Strongly Recommend

You do not need to test every getter in the application, but you should at least cover these scenarios:

- creating a valid hunter;
- creating an invalid hunter;
- retrieving available contracts;
- accepting a contract;
- rejecting acceptance when the hunter level is too low;
- rejecting acceptance when the contract is already accepted;
- completing a contract;
- granting the reward;
- preventing the same contract from being completed twice;
- preventing a hunter from completing someone else's contract.

Also try to include at least **one real Spring integration test** that goes through several layers of the application.

---

# 🏆 Certification Mode

To make the kata especially useful for a Spring certification, impose one rule on yourself:

**every time you use a Spring annotation, you should be able to explain what it does.**

For example, at the end of the kata, look at the annotations used in your project:

```
@SpringBootApplication
@RestController
@RequestMapping
@Service
@Repository
@Entity
@Id
@GeneratedValue
@Transactional
@Valid
...
```

And for each one, ask yourself:

> "What does Spring actually do because of this annotation?"

Then move on to some slightly more subtle questions:

> Why does `@Transactional` work on some methods but not in certain internal method calls?

> What is the actual difference between `@Component`, `@Service`, and `@Repository`?

> What turns `@Valid` validation failures into HTTP errors?

> How does Spring Data create an implementation of my repository even though I never wrote one?

> How does Spring Boot decide to automatically configure a `DataSource`?

This is where the kata becomes much more useful for certification preparation: **you start from concrete behavior you implemented and work your way back to how the framework actually works.**

### ⭐ Personal Score

```
⭐        User Story 1 completed
⭐⭐       User Stories 1-2 completed
⭐⭐⭐      User Stories 1-3 completed
⭐⭐⭐⭐     User Stories 1-4 completed
⭐⭐⭐⭐⭐    Everything + Final Boss
💀        Everything + clean integration tests
👑        You can explain what Spring does behind your annotations
```

I recommend that you **do not generate the entire skeleton with your IDE from the beginning**.

Start with a minimal Spring Initializr project and let the needs of the User Stories naturally lead you to create your controllers, services, repositories, exceptions, and transactions.

That will be much more educational.