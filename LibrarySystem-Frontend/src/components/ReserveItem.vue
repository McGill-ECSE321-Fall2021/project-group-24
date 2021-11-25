<template>
  <div>
    <h1>Reserve item:</h1>
    <a-card
      :key="item.itemNumber"
      style="align-items: center; margin-top: 20px"
    >
      <a-card-grid
        style="width: 25%; text-align: center; align-self: center"
        :hoverable="false"
      >
        <img
          v-if="item.imageURL"
          :alt="item.imageTitle"
          :src="item.imageURL"
          style="width: 100%"
        />
        <img
          v-if="!item.imageURL"
          :alt="item.imageTitle"
          style="width: 100%"
          src="https://islandpress.org/sites/default/files/default_book_cover_2015.jpg"
        />
      </a-card-grid>
      <a-card-grid
        :hoverable="false"
        :bordered="false"
        style="width: 75%; text-align: center"
      >
        <h4>{{ item.itemTitle }}</h4>
      </a-card-grid>
      <a-layout
        style="
          padding-left: 5%;
          padding-right: 5%;
          padding-top: 2.5%;
          width: 75%;
          background-color: white;
        "
      >
        <p style="text-align: left">{{ item.description }}</p>
        <p style="text-align: left">
          <span v-if="item.author">
            <br />
            <strong>Author:</strong> {{ item.author }}
          </span>
          <span v-if="item.artist">
            <br />
            <strong>Artist:</strong>
            {{ item.artist }}
          </span>
          <span v-if="item.publisher">
            <br />
            <strong>Publisher:</strong> {{ item.publisher }}
          </span>
          <span v-if="item.recordingLabel">
            <br />
            <strong>Recording Label:</strong>
            {{ item.recordingLabel }}
          </span>
          <span v-if="item.productionCompany">
            <strong>Production Company:</strong>
            {{ item.productionCompany }}
          </span>
          <span v-if="item.movieCase">
            <br />
            <strong>Movie Cast:</strong> {{ item.movieCase }}
          </span>
          <span v-if="item.director">
            <br />
            <strong>Director:</strong> {{ item.director }}
          </span>
          <span v-if="item.issueNumber">
            <br />
            <strong>Issue Number:</strong> {{ item.issueNumber }}
          </span>
          <span v-if="item.producer">
            <br />
            <strong>Producer:</strong> {{ item.producer }}
          </span>
          <span v-if="item.publisher">
            <br />
            <strong>Publisher:</strong> {{ item.publisher }}
          </span>
          <br />
          <strong>Genre:</strong> {{ item.genre }}
          <br />
          <strong>Publish date:</strong> {{ item.publishDate }}
          <br />
          <span v-if="item.isReservable"
            ><strong>Next Availabile date:</strong>
            {{ item.nextAvailableDate }}</span
          >
        </p>
      </a-layout>
    </a-card>
    <h1>For patron:</h1>
    <div style="padding-right: 20%; padding-left: 20%">
      <a-form :form="form" @submit="handleSubmit">
        <a-form-item>
          <a-input
            :disabled="this.currentUser.isPatron"
            :placeholder="
              this.currentUser.isPatron
                ? this.currentUser.idNum
                : 'Patron\'s idNumber'
            "
            v-decorator="[
              'idNum',
              {
                rules: [
                  {
                    required: !this.currentUser.isPatron,
                    message: 'Input patron idNum!',
                  },
                ],
              },
            ]"
          >
            <a-icon slot="prefix" type="user" />
          </a-input>
        </a-form-item>
        <h1>For date: {{ item.nextAvailableDate }}</h1>
        <a-form-item>
          <a-button html-type="submit">Confirm reservation</a-button>
        </a-form-item>
      </a-form>
      <a-modal
        v-model="modalVisible"
        title="Error"
        :footer="null"
        :header="null"
      >
        <a-alert
          v-if="this.error"
          message=" "
          :description="this.error"
          type="error"
          show-icon
        />
        <a-alert
          v-if="this.response"
          message=" "
          :description="this.response"
          type="success"
          show-icon
        />
      </a-modal>
    </div>
  </div>
</template>
;

<script src="../js/ReserveItem.js"></script>
;
