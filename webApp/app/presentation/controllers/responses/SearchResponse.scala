package presentation.controllers.responses

import domain.gym.Gym
import domain.search.MetaData
import useCase.search.SearchCondition

case class SearchResponse(
                         tookTime: Long,
                         totalHits: Long,
                         gyms: List[Gym],
                         condition: SearchCondition,
                         meta: MetaData
                         ) {

}
