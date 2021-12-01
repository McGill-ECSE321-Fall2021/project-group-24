<template>
  <!-- This is the template for the form 
it uses ant design and the tabs to create a form for 5 different items and to get their inputs -->
  <div>
    <h4>Select which type of item to create:</h4>
    <a-tabs default-active-key="Book" @change="callback">
      <a-tab-pane key="Book" tab="Book" force-render
        ><div id="tabTile"><p>Create a new book!</p></div>
        <div id="form">
          <a-form
            :form="formForBook"
            :label-col="{ span: 5 }"
            :wrapper-col="{ span: 12 }"
            @submit="handleBookSubmit"
          >
            <a-form-item label="Item Title">
              <a-input
                v-decorator="[
                  'itemTitle',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Please enter an item title!',
                      },
                    ],
                  },
                ]"
              />
            </a-form-item>
            <a-form-item label="Description">
              <a-input
                v-decorator="[
                  'description',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Please enter a description!',
                      },
                    ],
                  },
                ]"
              />
            </a-form-item>
            <a-form-item label="Image URL">
              <a-input
                v-decorator="[
                  'imageURL',
                  {
                    rules: [
                      { required: true, message: 'Please enter an image URL!' },
                    ],
                  },
                ]"
              />
            </a-form-item>
            <a-form-item label="Publisher">
              <a-input
                v-decorator="[
                  'publisher',
                  {
                    rules: [
                      { required: true, message: 'Please enter an publisher!' },
                    ],
                  },
                ]"
              />
            </a-form-item>
            <a-form-item label="Author">
              <a-input
                v-decorator="[
                  'author',
                  {
                    rules: [
                      { required: true, message: 'Please enter a author!' },
                    ],
                  },
                ]"
              />
            </a-form-item>
            <a-form-item label="Genre">
              <a-input
                v-decorator="[
                  'genre',
                  {
                    rules: [
                      { required: true, message: 'Please enter a genre!' },
                    ],
                  },
                ]"
              />
            </a-form-item>
            <a-form-item label="Publish Date">
              <a-input
                v-decorator="[
                  'publishDate',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Please enter a publish date!',
                      },
                    ],
                  },
                ]"
              />
            </a-form-item>

            <a-form-item default-value="yes" label="Reservable?">
              <a-radio-group
                v-decorator="[
                  'reserveGroupButtons',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Please pick if it is reservable or not!',
                      },
                    ],
                  },
                ]"
              >
                <a-radio-button value="yes"> Yes </a-radio-button>
                <a-radio-button value="no"> No </a-radio-button>
              </a-radio-group>
            </a-form-item>

            <a-form-item :wrapper-col="{ span: 12, offset: 5 }">
              <a-button type="primary" html-type="submit"> Create </a-button>
            </a-form-item>
          </a-form>
        </div>
      </a-tab-pane>
      <a-tab-pane key="Movie" tab="Movie" force-render>
        <div id="tabTile"><p>Create a new movie!</p></div>
        <div id="form">
          <a-form
            :form="formForMovie"
            :label-col="{ span: 5 }"
            :wrapper-col="{ span: 12 }"
            @submit="handleMovieSubmit"
          >
            <a-form-item label="Item Title">
              <a-input
                v-decorator="[
                  'itemTitle',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Please enter an item title!',
                      },
                    ],
                  },
                ]"
              />
            </a-form-item>
            <a-form-item label="Description">
              <a-input
                v-decorator="[
                  'description',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Please enter a description!',
                      },
                    ],
                  },
                ]"
              />
            </a-form-item>
            <a-form-item label="Image URL">
              <a-input
                v-decorator="[
                  'imageURL',
                  {
                    rules: [
                      { required: true, message: 'Please enter an image URL!' },
                    ],
                  },
                ]"
              />
            </a-form-item>
            <a-form-item label="Genre">
              <a-input
                v-decorator="[
                  'genre',
                  {
                    rules: [
                      { required: true, message: 'Please enter a genre!' },
                    ],
                  },
                ]"
              />
            </a-form-item>
            <a-form-item label="Publish Date">
              <a-input
                v-decorator="[
                  'publishDate',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Please enter a publish date!',
                      },
                    ],
                  },
                ]"
              />
            </a-form-item>
            <a-form-item default-value="yes" label="Reservable?">
              <a-radio-group
                v-decorator="[
                  'reserveGroupButtons',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Please pick if it is reservable or not!',
                      },
                    ],
                  },
                ]"
              >
                <a-radio-button value="yes"> Yes </a-radio-button>
                <a-radio-button value="no"> No </a-radio-button>
              </a-radio-group>
            </a-form-item>
            <a-form-item label="Movie Cast">
              <a-input
                v-decorator="[
                  'movieCast',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Please enter the movie cast!',
                      },
                    ],
                  },
                ]"
              />
            </a-form-item>

            <a-form-item label="Production Company">
              <a-input
                v-decorator="[
                  'productionCompany',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Please enter the production company!',
                      },
                    ],
                  },
                ]"
              />
            </a-form-item>

            <a-form-item label="Director">
              <a-input
                v-decorator="[
                  'director',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Please enter the director!',
                      },
                    ],
                  },
                ]"
              />
            </a-form-item>

            <a-form-item label="Producer">
              <a-input
                v-decorator="[
                  'producer',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Please enter the producer!',
                      },
                    ],
                  },
                ]"
              />
            </a-form-item>

            <a-form-item :wrapper-col="{ span: 12, offset: 5 }">
              <a-button type="primary" html-type="submit"> Create </a-button>
            </a-form-item>
          </a-form>
        </div>
      </a-tab-pane>
      <a-tab-pane key="Archive" tab="Archive" force-render>
        <div id="tabTile"><p>Create a new archive!</p></div>
        <div id="form">
          <a-form
            :form="formForArchive"
            :label-col="{ span: 5 }"
            :wrapper-col="{ span: 12 }"
            @submit="handleArchiveSubmit"
          >
            <a-form-item label="Item Title">
              <a-input
                v-decorator="[
                  'itemTitle',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Please enter an item title!',
                      },
                    ],
                  },
                ]"
              />
            </a-form-item>
            <a-form-item label="Description">
              <a-input
                v-decorator="[
                  'description',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Please enter a description!',
                      },
                    ],
                  },
                ]"
              />
            </a-form-item>
            <a-form-item label="Image URL">
              <a-input
                v-decorator="[
                  'imageURL',
                  {
                    rules: [
                      { required: true, message: 'Please enter an image URL!' },
                    ],
                  },
                ]"
              />
            </a-form-item>

            <a-form-item label="Genre">
              <a-input
                v-decorator="[
                  'genre',
                  {
                    rules: [
                      { required: true, message: 'Please enter a genre!' },
                    ],
                  },
                ]"
              />
            </a-form-item>
            <a-form-item label="Publish Date">
              <a-input
                v-decorator="[
                  'publishDate',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Please enter a publish date!',
                      },
                    ],
                  },
                ]"
              />
            </a-form-item>

            <a-form-item default-value="yes" label="Reservable?">
              <a-radio-group
                v-decorator="[
                  'reserveGroupButtons',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Please pick if it is reservable or not!',
                      },
                    ],
                  },
                ]"
              >
                <a-radio-button value="yes"> Yes </a-radio-button>
                <a-radio-button value="no"> No </a-radio-button>
              </a-radio-group>
            </a-form-item>

            <a-form-item :wrapper-col="{ span: 12, offset: 5 }">
              <a-button type="primary" html-type="submit"> Create </a-button>
            </a-form-item>
          </a-form>
        </div>
      </a-tab-pane>
      <a-tab-pane key="Music Album" tab="Music Album" force-render>
        <div id="tabTile"><p>Create a new music album!</p></div>
        <div id="form">
          <a-form
            :form="formForMusicAlbum"
            :label-col="{ span: 5 }"
            :wrapper-col="{ span: 12 }"
            @submit="handleMusicAlbumSubmit"
          >
            <a-form-item label="Item Title">
              <a-input
                v-decorator="[
                  'itemTitle',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Please enter an item title!',
                      },
                    ],
                  },
                ]"
              />
            </a-form-item>
            <a-form-item label="Description">
              <a-input
                v-decorator="[
                  'description',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Please enter a description!',
                      },
                    ],
                  },
                ]"
              />
            </a-form-item>
            <a-form-item label="Image URL">
              <a-input
                v-decorator="[
                  'imageURL',
                  {
                    rules: [
                      { required: true, message: 'Please enter an image URL!' },
                    ],
                  },
                ]"
              />
            </a-form-item>

            <a-form-item label="Genre">
              <a-input
                v-decorator="[
                  'genre',
                  {
                    rules: [
                      { required: true, message: 'Please enter a genre!' },
                    ],
                  },
                ]"
              />
            </a-form-item>
            <a-form-item label="Publish Date">
              <a-input
                v-decorator="[
                  'publishDate',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Please enter a publish date!',
                      },
                    ],
                  },
                ]"
              />
            </a-form-item>

            <a-form-item default-value="yes" label="Reservable?">
              <a-radio-group
                v-decorator="[
                  'reserveGroupButtons',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Please pick if it is reservable or not!',
                      },
                    ],
                  },
                ]"
              >
                <a-radio-button value="yes"> Yes </a-radio-button>
                <a-radio-button value="no"> No </a-radio-button>
              </a-radio-group>
            </a-form-item>

            <a-form-item label="Artist">
              <a-input
                v-decorator="[
                  'artist',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Please enter an artist!',
                      },
                    ],
                  },
                ]"
              />
            </a-form-item>

            <a-form-item label="Recording Label">
              <a-input
                v-decorator="[
                  'recordingLabel',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Please enter a recording label!',
                      },
                    ],
                  },
                ]"
              />
            </a-form-item>

            <a-form-item :wrapper-col="{ span: 12, offset: 5 }">
              <a-button type="primary" html-type="submit"> Create </a-button>
            </a-form-item>
          </a-form>
        </div>
      </a-tab-pane>
      <a-tab-pane key="Printed Media" tab="Printed Media" force-render>
        <div id="tabTile"><p>Create a new printed media!</p></div>
        <div id="form">
          <a-form
            :form="formForPrintedMedia"
            :label-col="{ span: 5 }"
            :wrapper-col="{ span: 12 }"
            @submit="handlePrintedMediaSubmit"
          >
            <a-form-item label="Item Title">
              <a-input
                v-decorator="[
                  'itemTitle',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Please enter an item title!',
                      },
                    ],
                  },
                ]"
              />
            </a-form-item>
            <a-form-item label="Description">
              <a-input
                v-decorator="[
                  'description',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Please enter a description!',
                      },
                    ],
                  },
                ]"
              />
            </a-form-item>
            <a-form-item label="Image URL">
              <a-input
                v-decorator="[
                  'imageURL',
                  {
                    rules: [
                      { required: true, message: 'Please enter an image URL!' },
                    ],
                  },
                ]"
              />
            </a-form-item>

            <a-form-item label="Genre">
              <a-input
                v-decorator="[
                  'genre',
                  {
                    rules: [
                      { required: true, message: 'Please enter a genre!' },
                    ],
                  },
                ]"
              />
            </a-form-item>
            <a-form-item label="Publish Date">
              <a-input
                v-decorator="[
                  'publishDate',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Please enter a publish date!',
                      },
                    ],
                  },
                ]"
              />
            </a-form-item>

            <a-form-item default-value="yes" label="Reservable?">
              <a-radio-group
                v-decorator="[
                  'reserveGroupButtons',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Please pick if it is reservable or not!',
                      },
                    ],
                  },
                ]"
              >
                <a-radio-button value="yes"> Yes </a-radio-button>
                <a-radio-button value="no"> No </a-radio-button>
              </a-radio-group>
            </a-form-item>

            <a-form-item label="Issue Number">
              <a-input
                v-decorator="[
                  'issueNumber',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Please enter an issue number!',
                      },
                    ],
                  },
                ]"
              />
            </a-form-item>

            <a-form-item :wrapper-col="{ span: 12, offset: 5 }">
              <a-button type="primary" html-type="submit"> Create </a-button>
            </a-form-item>
          </a-form>
        </div>
      </a-tab-pane>
    </a-tabs>
    <div id="searchbar">
      <a-modal v-model="visible" title=" " :footer="null" header="Message">
        <a-alert
          v-if="this.responseStatus == '200'"
          message="Successfully Created!"
          type="success"
          show-icon
        />
        <a-alert
          v-if="this.responseStatus != '200'"
          :message="this.roomError"
          type="error"
          show-icon
        />
      </a-modal>
    </div>
  </div>
</template>
<script src="../js/CreateNewItem.js"></script>
<style>
#input {
  padding-left: 20%;
  padding-right: 20%;
}

#tabTile {
  font-size: 200%;
}
#form {
  justify-content: center;
  align-items: center;
  align-content: center;
  justify-items: center;
  margin-left: 100px;
}
</style>
